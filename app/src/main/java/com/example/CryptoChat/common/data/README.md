# Data models

## Structure
There are 3 components in data model. 
- **Model** represents the structure of data instances. It may include multiple attributes, and set/get methods
- **Provider** manages a collection of data instance, it provides methods such as "CRUD" and exchange data with database using `services` module
- **Adapter** connects data sources with views. The main function of adapter is to render a view based on the data instance, and provide "CRUD" interfaces. The actual "CRUD" operations are relayed to **Provider**

## Base provider and concrete provider
Base provider has implemented some basic methods like "CRUD", but it does not specify the data source. But `BaseProvider` provides some generalization for the provider schema, which allows us to implement something like `LocalProvider`, `FirebaseProvider` or even `FakeProvider`.


## Existing Adapters
`MessagesListAdapter` and `DialogsListAdapter` are already provided by ChatKit library, but we need to extend them ideally to provide more flexibility. Refer to [source code](https://github.com/stfalcon-studio/ChatKit) to see which methods need to be overriden.