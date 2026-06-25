# Frontend API Referansı

Bu doküman, Next.js (React) frontend'i için backend API endpoint'lerinin tam listesini, request/response şemalarını ve yetkilendirme kurallarını içerir.

## Genel Bilgiler

- **Base URL**: `http://localhost:8080` (geliştirme ortamında)
- **Auth**: JWT tabanlı. Login sonrası `accessToken` response body'de, `refreshToken` HttpOnly cookie olarak döner.
- **Authorization Header**: `Authorization: Bearer <accessToken>`
- **Content-Type**: `application/json` (ProductImage hariç)
- **Response Format**: `ResponseEntity<T>`
  - Başarılı: `200 OK` + body
  - Bad Request: `400 Bad Request` + `false` (Boolean dönenlerde)
  - Not Found: `404 Not Found`
  - Unauthorized: `401 Unauthorized`
  - Forbidden: `403 Forbidden` (yetkisiz rol)

## JWT Token Payload

```json
{
  "sub": "user@example.com",
  "role": "ADMIN",
  "iat": 1234567890,
  "exp": 1234567890
}
```

Geçerli roller: `ADMIN`, `EDITOR`, `VIEWER`.

---

## 1. Auth (`/auth/**`)

Tüm `/auth/**` endpoint'leri public'tir.

### POST `/auth/register`

**Request Body:**

```json
{
  "userFirstName": "Berkan",
  "userLastName": "Test",
  "userEmail": "berkan@example.com",
  "password": "12345678",
  "roleId": 1
}
```

**Response:** `boolean`

### POST `/auth/login`

**Request Body:**

```json
{
  "userEmail": "berkan@example.com",
  "password": "12345678"
}
```

**Response:**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs..."
}
```

> Not: Aynı zamanda `refreshToken` HttpOnly cookie olarak set edilir.

### POST `/auth/refresh`

Cookie'deki `refreshToken` ile yeni access token üretir.

**Response:** `AuthResponse` (yeni access token)

### POST `/auth/logout`

Refresh token'ı cookie'den temizler ve veritabanından siler.

**Response:** `boolean`

---

## 2. Users (`/users`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/users/get-all` | GET | ADMIN | - | `UserDto[]` |
| `/users/me` | GET | Authenticated | - | `UserDto` |
| `/users/insert` | POST | ADMIN | `UserDtoUI` | `boolean` |

### UserDto

```json
{
  "userFirstName": "Berkan",
  "userLastName": "Test",
  "userPhoneNumber": "5551234567",
  "userEmail": "berkan@example.com",
  "deleted": false,
  "createdDate": "2026-01-01T00:00:00.000+00:00"
}
```

### UserDtoUI

```json
{
  "userFirstName": "Berkan",
  "userLastName": "Test",
  "userEmail": "berkan@example.com",
  "password": "12345678",
  "roleId": 1
}
```

---

## 3. Roles (`/roles`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/roles/all` | GET | ADMIN | - | `RoleDto[]` |
| `/roles/get/{roleName}` | GET | ADMIN | - | `long` |
| `/roles/add` | POST | ADMIN | `RoleDto` | `boolean` |
| `/roles/delete/{id}` | DELETE | ADMIN | - | `boolean` |

### RoleDto

```json
{
  "id": 1,
  "roleName": "Admin",
  "roleDescription": "System administrator"
}
```

---

## 4. Categories (`/categories`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/categories/all` | GET | ADMIN, EDITOR, VIEWER | - | `CategoryDto[]` |
| `/categories/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `CategoryDto` |
| `/categories/add` | POST | ADMIN, EDITOR | `CategoryDtoUI` | `boolean` |
| `/categories/update/{id}` | PUT | ADMIN, EDITOR | `CategoryDtoUI` | `boolean` |
| `/categories/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/categories/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### CategoryDto

```json
{
  "id": 1,
  "name": "Elektronik",
  "categoryName": "Elektronik",
  "slug": "elektronik",
  "active": true,
  "deleted": false
}
```

### CategoryDtoUI

```json
{
  "name": "Elektronik",
  "categoryName": "Elektronik",
  "slug": "elektronik",
  "active": true
}
```

> `slug` opsiyoneldir. Boş/null gönderilirse `categoryName`'den otomatik üretilir.

---

## 5. Products (`/products`)

### 5.1 Ürün CRUD

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/products/all` | GET | ADMIN, EDITOR, VIEWER | - | `ProductDto[]` |
| `/products/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `ProductDto` |
| `/products/add` | POST | ADMIN, EDITOR | `ProductDtoUI` | `boolean` |
| `/products/update/{id}` | PUT | ADMIN, EDITOR | `ProductDtoUI` | `boolean` |
| `/products/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/products/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### ProductDto

```json
{
  "id": 1,
  "productName": "iPhone 15",
  "slug": "iphone-15",
  "productDescription": "Apple iPhone 15",
  "active": true,
  "categoryId": 1,
  "categoryName": "Elektronik",
  "createdAt": "2026-01-01T00:00:00.000+00:00",
  "deleted": false,
  "productFeatures": [],
  "productImages": []
}
```

### ProductDtoUI

```json
{
  "productName": "iPhone 15",
  "slug": "iphone-15",
  "productDescription": "Apple iPhone 15",
  "active": true,
  "categoryId": 1
}
```

> `slug` opsiyoneldir. Boş/null gönderilirse `productName`'den otomatik üretilir.

### 5.2 Ürün Özellikleri (`/products/{id}/features`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/products/{id}/features` | GET | ADMIN, EDITOR, VIEWER | - | `ProductFeatureDto[]` |
| `/products/{id}/features/add` | POST | ADMIN, EDITOR | `ProductFeatureDtoUI` | `boolean` |
| `/products/{id}/features/update/{featureId}` | PUT | ADMIN, EDITOR | `ProductFeatureDtoUI` | `boolean` |
| `/products/{id}/features/delete/{featureId}` | DELETE | ADMIN | - | `boolean` |

### ProductFeatureDto

```json
{
  "id": 1,
  "productId": 1,
  "featureId": 1,
  "featureName": "Renk",
  "unitId": 1,
  "unitName": "Adet",
  "featureValue": "Siyah"
}
```

### ProductFeatureDtoUI

```json
{
  "featureId": 1,
  "unitId": 1,
  "featureValue": "Siyah"
}
```

> `unitId` opsiyoneldir.

### 5.3 Ürün Görselleri (`/products/{id}/images`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/products/{id}/images` | GET | ADMIN, EDITOR, VIEWER | - | `ProductImageDto[]` |
| `/products/{id}/images/add` | POST | ADMIN, EDITOR | `multipart/form-data` | `boolean` |
| `/products/{id}/images/update/{imageId}` | PUT | ADMIN, EDITOR | `multipart/form-data` | `boolean` |
| `/products/{id}/images/delete/{imageId}` | DELETE | ADMIN | - | `boolean` |

### ProductImageDto

```json
{
  "id": 1,
  "productId": 1,
  "imagePath": "uploads/products/1/uuid.jpg",
  "sortOrder": 0,
  "main": true
}
```

### ProductImage Form Data (add/update)

```
Content-Type: multipart/form-data

file: <File>          (opsiyonel)
imagePath: string      (opsiyonel, sadece file yoksa kullanılır)
sortOrder: number      (örn: 0)
main: boolean           (örn: true)
```

> `main=true` gönderildiğinde, ürüne ait önceki ana görsel otomatik olarak `main=false` olur.

---

## 6. Features (`/features`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/features/all` | GET | ADMIN, EDITOR, VIEWER | - | `FeatureDto[]` |
| `/features/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `FeatureDto` |
| `/features/add` | POST | ADMIN, EDITOR | `FeatureDtoUI` | `boolean` |
| `/features/update/{id}` | PUT | ADMIN, EDITOR | `FeatureDtoUI` | `boolean` |
| `/features/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/features/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### FeatureDto

```json
{
  "id": 1,
  "featureName": "Renk",
  "deleted": false
}
```

### FeatureDtoUI

```json
{
  "featureName": "Renk"
}
```

---

## 7. Units (`/units`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/units/all` | GET | ADMIN, EDITOR, VIEWER | - | `UnitDto[]` |
| `/units/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `UnitDto` |
| `/units/add` | POST | ADMIN, EDITOR | `UnitDtoUI` | `boolean` |
| `/units/update/{id}` | PUT | ADMIN, EDITOR | `UnitDtoUI` | `boolean` |
| `/units/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/units/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### UnitDto

```json
{
  "id": 1,
  "unitName": "Kilogram",
  "deleted": false
}
```

### UnitDtoUI

```json
{
  "unitName": "Kilogram"
}
```

---

## 8. Posts (`/posts`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/posts/all` | GET | ADMIN, EDITOR, VIEWER | - | `PostDto[]` |
| `/posts/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `PostDto` |
| `/posts/add` | POST | ADMIN, EDITOR | `PostDtoUI` | `boolean` |
| `/posts/update/{id}` | PUT | ADMIN, EDITOR | `PostDtoUI` | `boolean` |
| `/posts/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/posts/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### PostDto

```json
{
  "id": 1,
  "postTitle": "Yeni Ürünlerimiz",
  "postContent": "<p>İçerik...</p>",
  "postUrl": "yeni-urunlerimiz",
  "active": true,
  "viewCount": 0,
  "publishedAt": "2026-01-01T00:00:00.000+00:00",
  "createdAt": "2026-01-01T00:00:00.000+00:00",
  "deleted": false,
  "categoryId": 1,
  "categoryName": "Blog"
}
```

### PostDtoUI

```json
{
  "postTitle": "Yeni Ürünlerimiz",
  "postContent": "<p>İçerik...</p>",
  "postUrl": "yeni-urunlerimiz",
  "active": true,
  "categoryId": 1
}
```

> `postUrl` opsiyoneldir. Boş/null gönderilirse `postTitle`'dan otomatik üretilir.

---

## 9. Pages (`/pages`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/pages/all` | GET | ADMIN, EDITOR, VIEWER | - | `PageDto[]` |
| `/pages/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `PageDto` |
| `/pages/add` | POST | ADMIN, EDITOR | `PageDtoUI` | `boolean` |
| `/pages/update/{id}` | PUT | ADMIN, EDITOR | `PageDtoUI` | `boolean` |
| `/pages/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/pages/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### PageDto

```json
{
  "id": 1,
  "pageName": "Hakkımızda",
  "pageOrder": 1,
  "pageUrl": "hakkimizda",
  "pageContent": "<p>...</p>",
  "metaTitle": "Hakkımızda",
  "metaDescription": "Şirket hakkında bilgi",
  "root": false,
  "active": true,
  "createdAt": "2026-01-01T00:00:00.000+00:00",
  "updatedDate": "2026-01-01T00:00:00.000+00:00",
  "deleted": false,
  "seoTitle": "Hakkımızda",
  "seoDescription": "Şirket hakkında bilgi",
  "seoKeywords": "hakkımızda, şirket",
  "parentPageId": null,
  "parentPageName": null
}
```

### PageDtoUI

```json
{
  "pageName": "Hakkımızda",
  "pageOrder": 1,
  "pageUrl": "hakkimizda",
  "pageContent": "<p>...</p>",
  "metaTitle": "Hakkımızda",
  "metaDescription": "Şirket hakkında bilgi",
  "root": false,
  "active": true,
  "seoTitle": "Hakkımızda",
  "seoDescription": "Şirket hakkında bilgi",
  "seoKeywords": "hakkımızda, şirket",
  "parentPageId": null
}
```

> `parentPageId` opsiyoneldir.

---

## 10. Countries (`/countries`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/countries/all` | GET | ADMIN, EDITOR, VIEWER | - | `CountryDto[]` |
| `/countries/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `CountryDto` |
| `/countries/add` | POST | ADMIN, EDITOR | `CountryDtoUI` | `boolean` |
| `/countries/update/{id}` | PUT | ADMIN, EDITOR | `CountryDtoUI` | `boolean` |
| `/countries/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/countries/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### CountryDto

```json
{
  "id": 1,
  "countryName": "Turkey",
  "countryCode": 90,
  "deleted": false
}
```

### CountryDtoUI

```json
{
  "countryName": "Turkey",
  "countryCode": 90
}
```

---

## 11. Cities (`/cities`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/cities/all` | GET | ADMIN, EDITOR, VIEWER | - | `CityDto[]` |
| `/cities/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `CityDto` |
| `/cities/add` | POST | ADMIN, EDITOR | `CityDtoUI` | `boolean` |
| `/cities/update/{id}` | PUT | ADMIN, EDITOR | `CityDtoUI` | `boolean` |
| `/cities/delete/{id}` | DELETE | ADMIN | - | `boolean` (soft delete) |
| `/cities/delete/{id}/hard` | DELETE | ADMIN | - | `boolean` (hard delete) |

### CityDto

```json
{
  "id": 1,
  "cityName": "Istanbul",
  "deleted": false
}
```

### CityDtoUI

```json
{
  "cityName": "Istanbul"
}
```

---

## 12. Images (`/images`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/images/all` | GET | ADMIN, EDITOR, VIEWER | - | `ImageDto[]` |
| `/images/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `ImageDto` |
| `/images/add` | POST | ADMIN, EDITOR | `multipart/form-data` | `boolean` |
| `/images/update/{id}` | PUT | ADMIN, EDITOR | `multipart/form-data` | `boolean` |
| `/images/delete/{id}` | DELETE | ADMIN | - | `boolean` (hard delete) |

### ImageDto

```json
{
  "id": 1,
  "imageName": "Banner",
  "imagePath": "uploads/products/images/uuid.jpg",
  "altText": "Ana sayfa banner görseli"
}
```

### Image Form Data (add/update)

```
Content-Type: multipart/form-data

file: <File>          (opsiyonel)
imageName: string      (opsiyonel)
imagePath: string      (opsiyonel, sadece file yoksa kullanılır)
altText: string        (opsiyonel)
```

> `file` gönderilirse `uploads/products/images/` altına kaydedilir ve `imagePath` olarak atanır. `file` gönderilmezse `imagePath` alanı kullanılır. Silme işleminde hem DB kaydı hem de fiziksel dosya silinir.

---

## 13. Sliders (`/sliders`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/sliders/all` | GET | ADMIN, EDITOR | - | `SliderDto[]` |
| `/sliders/active` | GET | ADMIN, EDITOR, VIEWER | - | `SliderDto[]` |
| `/sliders/get/{id}` | GET | ADMIN, EDITOR, VIEWER | - | `SliderDto` |
| `/sliders/add` | POST | ADMIN, EDITOR | `SliderDtoUI` | `boolean` |
| `/sliders/update/{id}` | PUT | ADMIN, EDITOR | `SliderDtoUI` | `boolean` |
| `/sliders/delete/{id}` | DELETE | ADMIN | - | `boolean` (hard delete) |

### SliderDto

```json
{
  "id": 1,
  "imageId": 1,
  "imagePath": "uploads/products/images/uuid.jpg",
  "productId": 1,
  "productName": "iPhone 15",
  "title": "Yeni Sezon Ürünleri",
  "sortOrder": 0,
  "active": true
}
```

### SliderDtoUI

```json
{
  "imageId": 1,
  "productId": 1,
  "title": "Yeni Sezon Ürünleri",
  "sortOrder": 0,
  "active": true
}
```

> `imageId` önceki adımda `/images/add` ile oluşturulmuş bir Image kaydının ID'si olmalıdır. `/sliders/active` endpoint'i sadece `active=true` olan slider'ları `sortOrder` sırasına göre döner.

---

## 14. Settings (`/settings`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/settings/all` | GET | Public | - | `SettingsDto[]` |
| `/settings/get/{id}` | GET | Public | - | `SettingsDto` |
| `/settings/add` | POST | Admin, Editor | `SettingsDtoUI` | `boolean` |
| `/settings/update/{id}` | PUT | Admin, Editor | `SettingsDtoUI` | `boolean` |
| `/settings/delete/{id}` | DELETE | Admin | - | `boolean` |

### SettingsDto

```json
{
  "id": 1,
  "instagramAddress": "https://instagram.com/example",
  "linkedinAddress": "https://linkedin.com/company/example",
  "facebookAddress": "https://facebook.com/example",
  "twitterAddress": "https://twitter.com/example",
  "youtubeAddress": "https://youtube.com/example",
  "tiktokAddress": "https://tiktok.com/@example",
  "whatsappAddress": "https://wa.me/905551234567",
  "telegramAddress": "https://t.me/example",
  "address": "Örnek Mah. Örnek Cad. No:1 İstanbul",
  "googleMap": "https://maps.google.com/?q=...",
  "email": "info@example.com",
  "phone": "+90 555 123 45 67",
  "createdAt": "2026-06-25T13:00:00.000+00:00",
  "updatedDate": "2026-06-25T13:00:00.000+00:00"
}
```

### SettingsDtoUI

```json
{
  "instagramAddress": "https://instagram.com/example",
  "linkedinAddress": "https://linkedin.com/company/example",
  "facebookAddress": "https://facebook.com/example",
  "twitterAddress": "https://twitter.com/example",
  "youtubeAddress": "https://youtube.com/example",
  "tiktokAddress": "https://tiktok.com/@example",
  "whatsappAddress": "https://wa.me/905551234567",
  "telegramAddress": "https://t.me/example",
  "address": "Örnek Mah. Örnek Cad. No:1 İstanbul",
  "googleMap": "https://maps.google.com/?q=...",
  "email": "info@example.com",
  "phone": "+90 555 123 45 67"
}
```

> Settings genellikle tek bir kayıt olarak yönetilir. Tüm alanlar opsiyoneldir; gönderilmeyen alanlar `null` olarak kaydedilir. `email` alanı gönderilirse geçerli bir e-posta formatında olmalıdır.

---

## TypeScript Interface'leri

```typescript
// types/api.ts

export interface UserDto {
  userFirstName: string;
  userLastName: string;
  userPhoneNumber?: string;
  userEmail: string;
  deleted: boolean;
  createdDate: string;
}

export interface UserDtoUI {
  userFirstName: string;
  userLastName: string;
  userEmail: string;
  password: string;
  roleId: number;
}

export interface RoleDto {
  id: number;
  roleName: string;
  roleDescription?: string;
}

export interface CategoryDto {
  id: number;
  name: string;
  categoryName: string;
  slug: string;
  active: boolean;
  deleted: boolean;
}

export interface CategoryDtoUI {
  name: string;
  categoryName: string;
  slug?: string;
  active: boolean;
}

export interface ProductDto {
  id: number;
  productName: string;
  slug: string;
  productDescription: string;
  active: boolean;
  categoryId: number;
  categoryName: string;
  createdAt: string;
  deleted: boolean;
  productFeatures: ProductFeatureDto[];
  productImages: ProductImageDto[];
}

export interface ProductDtoUI {
  productName: string;
  slug?: string;
  productDescription: string;
  active: boolean;
  categoryId: number;
}

export interface ProductFeatureDto {
  id: number;
  productId: number;
  featureId: number;
  featureName: string;
  unitId?: number;
  unitName?: string;
  featureValue: string;
}

export interface ProductFeatureDtoUI {
  featureId: number;
  unitId?: number;
  featureValue: string;
}

export interface ProductImageDto {
  id: number;
  productId: number;
  imagePath: string;
  sortOrder: number;
  main: boolean;
}

export interface ProductImageDtoUI {
  file?: File;
  imagePath?: string;
  sortOrder: number;
  main: boolean;
}

export interface FeatureDto {
  id: number;
  featureName: string;
  deleted: boolean;
}

export interface FeatureDtoUI {
  featureName: string;
}

export interface UnitDto {
  id: number;
  unitName: string;
  deleted: boolean;
}

export interface UnitDtoUI {
  unitName: string;
}

export interface PostDto {
  id: number;
  postTitle: string;
  postContent: string;
  postUrl: string;
  active: boolean;
  viewCount: number;
  publishedAt: string;
  createdAt: string;
  deleted: boolean;
  categoryId: number;
  categoryName: string;
}

export interface PostDtoUI {
  postTitle: string;
  postContent: string;
  postUrl?: string;
  active: boolean;
  categoryId: number;
}

export interface PageDto {
  id: number;
  pageName: string;
  pageOrder: number;
  pageUrl: string;
  pageContent: string;
  metaTitle: string;
  metaDescription: string;
  root: boolean;
  active: boolean;
  createdAt: string;
  updatedDate: string;
  deleted: boolean;
  seoTitle?: string;
  seoDescription?: string;
  seoKeywords?: string;
  parentPageId?: number;
  parentPageName?: string;
}

export interface PageDtoUI {
  pageName: string;
  pageOrder: number;
  pageUrl: string;
  pageContent: string;
  metaTitle: string;
  metaDescription: string;
  root: boolean;
  active: boolean;
  seoTitle?: string;
  seoDescription?: string;
  seoKeywords?: string;
  parentPageId?: number;
}

export interface CountryDto {
  id: number;
  countryName: string;
  countryCode: number;
  deleted: boolean;
}

export interface CountryDtoUI {
  countryName: string;
  countryCode: number;
}

export interface CityDto {
  id: number;
  cityName: string;
  deleted: boolean;
}

export interface CityDtoUI {
  cityName: string;
}

export interface ImageDto {
  id: number;
  imageName?: string;
  imagePath: string;
  altText?: string;
}

export interface ImageDtoUI {
  file?: File;
  imageName?: string;
  imagePath?: string;
  altText?: string;
}

export interface SliderDto {
  id: number;
  imageId: number;
  imagePath: string;
  productId: number;
  productName: string;
  title: string;
  sortOrder: number;
  active: boolean;
}

export interface SliderDtoUI {
  imageId: number;
  productId: number;
  title: string;
  sortOrder: number;
  active: boolean;
}

export interface SettingsDto {
  id: number;
  instagramAddress?: string;
  linkedinAddress?: string;
  facebookAddress?: string;
  twitterAddress?: string;
  youtubeAddress?: string;
  tiktokAddress?: string;
  whatsappAddress?: string;
  telegramAddress?: string;
  address?: string;
  googleMap?: string;
  email?: string;
  phone?: string;
  createdAt: string;
  updatedDate: string;
}

export interface SettingsDtoUI {
  instagramAddress?: string;
  linkedinAddress?: string;
  facebookAddress?: string;
  twitterAddress?: string;
  youtubeAddress?: string;
  tiktokAddress?: string;
  whatsappAddress?: string;
  telegramAddress?: string;
  address?: string;
  googleMap?: string;
  email?: string;
  phone?: string;
}

export interface AuthResponse {
  accessToken: string;
}

export interface AuthRequest {
  userEmail: string;
  password: string;
}

export interface RegisterResponseDto {
  success: boolean;
  message?: string;
}
```

---

## Önemli Notlar

1. **Slug/URL üretimi**: `Product`, `Category`, `Post` oluştururken `slug`/`postUrl` alanları opsiyoneldir. Boş/null gönderilirse başlıktan otomatik URL üretilir. Dolu gönderilirse olduğu gibi kullanılır.
2. **Soft delete**: `DELETE /{resource}/{id}` kaydı `deleted=true` yapar. `DELETE /{resource}/{id}/hard` kalıcı siler.
3. **Multipart upload**: `ProductImage` ve `Image` ekleme/güncelleme istekleri `multipart/form-data` olmalıdır. `file` alanı opsiyoneldir; gönderilmezse `imagePath` kullanılır.
4. **Rol bazlı yetkilendirme**: Backend `@PreAuthorize` ile korunmaktadır. Frontend'de de rol bazlı navigasyon/aksiyon gizleme yapılmalıdır.
5. **Zorunlu alanlar**: `Boolean` wrapper tipleri (`active`, `root` vb.) backend tarafında `@NotNull` ile zorunludur; frontend'den eksik gönderilmemelidir.
