# Restorium

FastAsyncWorldEdit entegrasyonlu gelişmiş arena sıfırlama sistemi.

## Özellikler

- Otomatik Arena Sıfırlama - Belirlediğiniz zaman aralıklarında otomatik şematik yapıştırma
- Esnek Zamanlama - Saat, dakika ve saniye bazlı zamanlama desteği (1h30m, 45m, 2h gibi)
- Bölge Koruması - Wand aracı ile bölge seçimi ve reset sırasında oyuncu koruması
- Oyuncu Güvenliği - Reset sırasında bölgedeki oyuncular otomatik olarak spawn'a ışınlanır
- Minimal Görsel - Sessiz çalışma, sadece title ile emoji gösterimi
- Ses Geri Bildirimi - Reset tamamlandığında özelleştirilebilir başarı sesi
- PlaceholderAPI Desteği - Geri sayım timer'ı için placeholder
- Tam Özelleştirilebilir - Her detay config.yml'den ayarlanabilir

## Gereksinimler

- Spigot/Paper 1.8+
- FastAsyncWorldEdit (FAWE)
- PlaceholderAPI (opsiyonel, placeholder kullanımı için)

## Komutlar

 `/restorium reload` | Plugin ayarlarını yeniden yükler | `restorium.reload` |
 `/restorium wand` | Arena seçim değneğini verir | `restorium.wand` |

**Kısa Yollar:** `/resto`, `/rs`

## Yetkiler

- `restorium.reload` - Reload komutunu kullanabilme
- `restorium.wand` - Wand komutunu kullanabilme

## PlaceholderAPI

`%restorium_countdown%` - Arena resetine kalan süreyi akıllı formatta gösterir:
- 1 saat veya üzeri: "3 saat"
- 1 saatin altında: "45 dakika"
- 1 dakikanın altında: "30 saniye"
- Reset sırasında: "Sıfırlanıyor..."

## Kurulum

1. FastAsyncWorldEdit plugininin sunucunuzda kurulu olduğundan emin olun
2. Restorium.jar dosyasını plugins klasörüne atın
3. Sunucuyu başlatın veya /reload confirm ile yeniden yükleyin
4. config.yml dosyasını ihtiyacınıza göre düzenleyin
5. /restorium reload komutu ile ayarları yükleyin

## Yapılandırma

### Temel Ayarlar

```yaml
schematic:
  name: 'arena'
  world: 'world'
  x: 0
  y: 64
  z: 0

schedule:
  interval: '1h'
```

### Zamanlama Formatı

Esnek süre formatı kullanabilirsiniz:
- 1h = 1 saat
- 30m = 30 dakika
- 1h30m = 1 saat 30 dakika
- 2h15m30s = 2 saat 15 dakika 30 saniye
- 45s = 45 saniye

### Bölge Ayarları

```yaml
region:
  enabled: true
  world: 'world'
  pos1-x: -50
  pos1-y: 0
  pos1-z: -50
  pos2-x: 50
  pos2-y: 100
  pos2-z: 50
```

### Görsel ve Ses Ayarları

```yaml
visual:
  title:
    enabled: true
    emoji: '🕐'
    duration: 60
  
  sound:
    enabled: true
    type: 'ENTITY_PLAYER_LEVELUP'
    volume: 1.0
    pitch: 1.0
```

## Kullanım

### Adım 1: FAWE ile Şematik Oluşturma

1. Arena bölgenizi WorldEdit ile seçin (//wand veya //pos1, //pos2)
2. Şematiği kaydedin: //copy ardından //schem save arena
3. Şematik adını config.yml'de belirtin: name: 'arena'

### Adım 2: Bölge Belirleme

1. /restorium wand komutu ile değneği alın
2. Sol tık ile ilk köşeyi seçin (Pembe parçacık efekti)
3. Sağ tık ile ikinci köşeyi seçin (Mavi parçacık efekti)
4. Seçim otomatik olarak config.yml'e kaydedilir

### Adım 3: Reset Zamanını Ayarlama

```yaml
schedule:
  interval: '1h30m'
```

### Adım 4: Test Etme

Plugin otomatik olarak belirlenen süre sonunda arena'yı resetleyecektir. Reset sırasında:
- Tüm oyunculara saat emojisi title olarak gösterilir
- Bölgedeki oyuncular /spawn komutu ile ışınlanır
- Tamamlandığında başarı sesi duyulur

## PlaceholderAPI Kullanımı

Scoreboard, holograms veya diğer pluginlerde kullanabilirsiniz:

```yaml
# DeluxeMenus
display_name: '&eArena Reset: &f%restorium_countdown%'

# FeatherBoard
text:
  - '&b&lArena Resetine:'
  - '&f%restorium_countdown%'

# HolographicDisplays
lines:
  - '&6Sonraki Reset'
  - '&e%restorium_countdown%'
```

## Sorun Giderme

### FAWE bulunamadı hatası
- FastAsyncWorldEdit plugininin kurulu ve çalıştığından emin olun
- /plugins komutu ile FAWE'nin yeşil olduğunu kontrol edin

### Şematik yapıştırılmıyor
- Şematik adının doğru olduğundan emin olun (uzantı olmadan)
- FAWE şematik klasörünü kontrol edin: plugins/FastAsyncWorldEdit/schematics/
- Koordinatların doğru olduğundan emin olun

### Bölge çalışmıyor
- Wand ile tekrar seçim yapın
- /restorium reload ile ayarları yeniden yükleyin
- Console'dan hata mesajlarını kontrol edin

### Placeholder çalışmıyor
- PlaceholderAPI'nin kurulu olduğundan emin olun
- /papi register restorium komutunu deneyin
- Sunucuyu yeniden başlatın

## Teknik Detaylar

**Geliştirici:** Qwertydev  
**Versiyon:** 1.0  
**API:** Spigot/Paper 1.8+  
**Bağımlılıklar:** FastAsyncWorldEdit (zorunlu), PlaceholderAPI (opsiyonel)
