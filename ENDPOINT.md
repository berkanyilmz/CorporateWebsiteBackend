# API Endpoints

Bu doküman, projedeki Image ve Slider endpoint'lerinin özetini içerir. Detaylı API referansı için `docs/api-reference-for-frontend.md` dosyasına bakabilirsiniz.

---

## Images (`/images`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/images/all` | GET | Public | - | `ImageDto[]` |
| `/images/get/{id}` | GET | Public | - | `ImageDto` |
| `/images/add` | POST | Admin, Editor | `multipart/form-data` | `boolean` |
| `/images/update/{id}` | PUT | Admin, Editor | `multipart/form-data` | `boolean` |
| `/images/delete/{id}` | DELETE | Admin | - | `boolean` |

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
altText: string        (opsiyonel)
```

> `file` gönderilirse `uploads/products/images/` altına kaydedilir. Silme işleminde hem DB kaydı hem de fiziksel dosya silinir.

---

## Sliders (`/sliders`)

| Endpoint | Method | Yetki | Request | Response |
|----------|--------|-------|---------|----------|
| `/sliders/all` | GET | Admin, Editor | - | `SliderDto[]` |
| `/sliders/active` | GET | Public | - | `SliderDto[]` |
| `/sliders/get/{id}` | GET | Public | - | `SliderDto` |
| `/sliders/add` | POST | Admin, Editor | `SliderDtoUI` | `boolean` |
| `/sliders/update/{id}` | PUT | Admin, Editor | `SliderDtoUI` | `boolean` |
| `/sliders/delete/{id}` | DELETE | Admin | - | `boolean` |

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

## Settings (`/settings`)

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
  "headerLogo": "1",
  "footerLogo": "2",
  "favicon": "3",
  "companyName": "Örnek Şirket",
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
  "phone": "+90 555 123 45 67",
  "headerLogo": "1",
  "footerLogo": "2",
  "favicon": "3",
  "companyName": "Örnek Şirket"
}
```

> Settings genellikle tek bir kayıt olarak yönetilir. Tüm alanlar opsiyoneldir; gönderilmeyen alanlar `null` olarak kaydedilir. `email` alanı gönderilirse geçerli bir e-posta formatında olmalıdır.
