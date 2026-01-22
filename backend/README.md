## Data handling

The initial dataset is loaded from a CSV file and treated as read-only.

New persons created via `POST /persons` are stored in an in-memory repository and combined with the CSV data at runtime.
This keeps the CSV source immutable while still allowing the API to be extended with write operations.
