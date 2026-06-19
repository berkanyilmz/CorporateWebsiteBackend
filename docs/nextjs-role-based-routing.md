# Next.js ile Rol Bazlı Sayfa Görünürlüğü

Bu doküman, AdminPanelBackend ile birlikte kullanılacak Next.js (App Router) frontend'inde kullanıcı rollerine göre sayfa görünürlüğünün nasıl yönetileceğini açıklar.

## 1. JWT Token Yapısı

Login sonrası backend'den dönen access token JWT payload'ı aşağıdaki gibi olur:

```json
{
  "sub": "user@example.com",
  "role": "ADMIN",
  "iat": 1234567890,
  "exp": 1234567890
}
```

> Not: Backend `User.getAuthorities()` üzerinden kullanıcının veritabanındaki rolünü alır ve JWT'ye `role` claim'i olarak yazar. Spring Security yetkilendirmesi için granted authority `ROLE_ADMIN` şeklindedir, ancak JWT claim'inde `ADMIN` olarak saklanır.

Token'dan rolü okumak için `jwt-decode` kütüphanesi kullanılabilir:

```bash
npm install jwt-decode
```

```typescript
// lib/auth.ts
import { jwtDecode } from 'jwt-decode';

interface JwtPayload {
  sub: string;
  role: 'ADMIN' | 'EDITOR' | 'VIEWER';
  exp: number;
}

export function decodeToken(token: string): JwtPayload {
  return jwtDecode<JwtPayload>(token);
}

export function getUserRole(token?: string): string | null {
  if (!token) return null;
  try {
    return decodeToken(token).role;
  } catch {
    return null;
  }
}
```

## 2. Sayfa ve Navigasyon Konfigürasyonu

Sayfaları rollerine göre tanımlamak için merkezi bir config kullanılır:

```typescript
// config/navigation.ts
export type UserRole = 'ADMIN' | 'EDITOR' | 'VIEWER';

export interface NavItem {
  label: string;
  href: string;
  roles: UserRole[];
  icon?: string;
}

export const NAV_ITEMS: NavItem[] = [
  { label: 'Dashboard', href: '/dashboard', roles: ['ADMIN', 'EDITOR', 'VIEWER'] },
  { label: 'Kullanıcılar', href: '/users', roles: ['ADMIN'] },
  { label: 'Roller', href: '/roles', roles: ['ADMIN'] },
  { label: 'Ürünler', href: '/products', roles: ['ADMIN', 'EDITOR', 'VIEWER'] },
  { label: 'Ürün Ekle', href: '/products/add', roles: ['ADMIN', 'EDITOR'] },
  { label: 'Kategoriler', href: '/categories', roles: ['ADMIN', 'EDITOR', 'VIEWER'] },
  { label: 'Özellikler', href: '/features', roles: ['ADMIN', 'EDITOR', 'VIEWER'] },
  { label: 'Birimler', href: '/units', roles: ['ADMIN', 'EDITOR', 'VIEWER'] },
];

export function isRouteAllowed(path: string, role?: UserRole | null): boolean {
  const route = NAV_ITEMS.find((item) => item.href === path);
  if (!route) return true; // Konfigürasyonda olmayan sayfalar için ayrı kontrol yapılır
  if (!role) return false;
  return route.roles.includes(role);
}

export function getVisibleNavItems(role?: UserRole | null): NavItem[] {
  if (!role) return [];
  return NAV_ITEMS.filter((item) => item.roles.includes(role));
}
```

## 3. Middleware ile Sayfa Koruması

`middleware.ts` (App Router projesinin root'unda) ile kullanıcı giriş yapmamışsa login'e, yetkisi yoksa `/unauthorized` sayfasına yönlendirme yapılır:

```typescript
// middleware.ts
import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';
import { jwtDecode } from 'jwt-decode';
import { NAV_ITEMS, type UserRole } from './config/navigation';

const PUBLIC_PATHS = ['/login', '/register', '/unauthorized'];

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  if (PUBLIC_PATHS.includes(pathname)) {
    return NextResponse.next();
  }

  const token = request.cookies.get('accessToken')?.value;
  if (!token) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  let role: UserRole | null = null;
  try {
    const payload = jwtDecode<{ role: UserRole }>(token);
    role = payload.role;
  } catch {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  const route = NAV_ITEMS.find((item) => pathname === item.href || pathname.startsWith(`${item.href}/`));
  if (route && !route.roles.includes(role)) {
    return NextResponse.redirect(new URL('/unauthorized', request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/((?!api|_next/static|_next/image|favicon.ico).*)'],
};
```

> Not: `accessToken` cookie olarak saklanıyorsa `HttpOnly` olmamalıdır, çünkü client-side JavaScript tarafından okunması gerekir. Eğer `HttpOnly` cookie kullanılıyorsa, kullanıcı rolü SSR / Server Component içinde okunmalıdır.

## 4. Sidebar / Navbar'da Rol Bazlı Liste

```tsx
// components/Sidebar.tsx
'use client';

import Link from 'next/link';
import { useAuth } from '@/hooks/useAuth';
import { getVisibleNavItems } from '@/config/navigation';

export function Sidebar() {
  const { role } = useAuth();
  const items = getVisibleNavItems(role);

  return (
    <nav>
      {items.map((item) => (
        <Link key={item.href} href={item.href}>
          {item.label}
        </Link>
      ))}
    </nav>
  );
}
```

```typescript
// hooks/useAuth.ts
'use client';

import { useEffect, useState } from 'react';
import { getUserRole } from '@/lib/auth';
import type { UserRole } from '@/config/navigation';

export function useAuth() {
  const [role, setRole] = useState<UserRole | null>(null);

  useEffect(() => {
    const token = document.cookie
      .split('; ')
      .find((row) => row.startsWith('accessToken='))
      ?.split('=')[1];
    setRole(getUserRole(token) as UserRole | null);
  }, []);

  return { role };
}
```

## 5. Server Component'de Yetki Kontrolü

App Router'da sayfa sunucu bileşeni olarak da korunabilir:

```tsx
// app/products/add/page.tsx
import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';
import { jwtDecode } from 'jwt-decode';
import type { UserRole } from '@/config/navigation';

export default function AddProductPage() {
  const token = cookies().get('accessToken')?.value;
  if (!token) redirect('/login');

  let role: UserRole | null = null;
  try {
    role = jwtDecode<{ role: UserRole }>(token).role;
  } catch {
    redirect('/login');
  }

  if (!['ADMIN', 'EDITOR'].includes(role)) {
    redirect('/unauthorized');
  }

  return <div>Ürün Ekleme Formu</div>;
}
```

## 6. Buton ve Aksiyon Gizleme

Sadece sayfa erişimini değil, sayfa içindeki aksiyonları da rol bazlı gizleyebilirsiniz:

```tsx
// components/ProductActions.tsx
'use client';

import { useAuth } from '@/hooks/useAuth';

export function ProductActions({ productId }: { productId: string }) {
  const { role } = useAuth();

  return (
    <div>
      {role === 'ADMIN' && (
        <button onClick={() => deleteProduct(productId)}>Sil</button>
      )}
      {['ADMIN', 'EDITOR'].includes(role || '') && (
        <button onClick={() => editProduct(productId)}>Düzenle</button>
      )}
    </div>
  );
}
```

## 7. Backend ile Uyumlu Rol Haritası

Backend yetkilendirme şeması aşağıdaki gibidir:

| Rol | Yetkileri |
|-----|-----------|
| `ADMIN` | Tüm CRUD işlemleri, soft/hard delete, kullanıcı/rol yönetimi |
| `EDITOR` | Okuma, ekleme ve güncelleme; silme işlemleri yapamaz |
| `VIEWER` | Sadece okuma/listeleme |

Frontend bu haritaya göre hem navigasyonu hem de aksiyonları kısıtlamalıdır. Backend `@PreAuthorize` ile zaten yetkisiz istekleri engellediği için frontend sadece kullanıcı deneyimini iyileştirmek için kullanılır; asıl güvenlik backend'dedir.

## 8. Özet Akış

1. Kullanıcı `/auth/login` ile giriş yapar.
2. Backend access token döner; token içinde `role` claim'i bulunur.
3. Frontend token'ı cookie veya state'de saklar.
4. `middleware.ts` her istekte token ve rolü kontrol eder; yetkisiz erişimi yönlendirir.
5. Sidebar/navbar `getVisibleNavItems(role)` ile sadece uygun sayfaları gösterir.
6. Sayfa içindeki butonlar `role` değerine göre koşullu render edilir.
7. Backend `@PreAuthorize` ile yetkisiz API çağrılarını 403 ile engeller.
