# Currency Converter

This sample app demonstrates a basic currency converter built with Jetpack Compose.
It showcases three screens:

- **Currencies** – shows rates for a selected base currency updating every second. When editing the desired amount to buy, the list filters currencies according to account balances. Clicking another currency either selects it as base or proceeds to the exchange screen.
- **Exchange** – shows the details of a purchase and updates the Room database when confirmed.
- **Transactions** – lists past exchanges stored locally.

Room is prepopulated with a RUB account containing 75,000 units. Rates are provided by a fake remote service. Hilt provides dependencies.

### Interesting design choices
- The app uses Compose for the entire UI with a simple navigation graph.
- Country flag emojis and currency display names add readability.
- Database is bundled as an asset to simplify initial setup and to show an example of prepopulating Room.


