# Contribution Form

## Yunlu Wen (yunluw)
- Design & implement user interfaces
  - Reliable navigations with Fragment
  - Contact lists 
  - UI for debug purpose
  - UI for NFC communication
  - UI for contact setting
  - Theme
- Migrate thrid party chat ui library to make it work with local data models.
  - Make use of this library https://github.com/stfalcon-studio/ChatKit
  - Override adapters to fit our data model
  - Override models and connect them with SQLite (using GreenDao)
- Manage design patterns
  - Data management with Models -> DataProvider -> Adapter
  - Thread-safe singleton data provider connected to SQLite
