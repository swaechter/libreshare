CREATE TABLE account(
    id UUID NOT NULL,
    username TEXT NOT NULL,
    email_address TEXT NOT NULL,
    password_hash TEXT NOT NULL,
    CONSTRAINT account_primarykey PRIMARY KEY(id),
    CONSTRAINT account_unique_username UNIQUE(username)
);

CREATE TABLE file_item(
    id UUID NOT NULL,
    parent_id UUID NOT NULL,
    type TEXT NOT NULL,
    name TEXT NOT NULL,
    size BIGINT NOT NULL,
    CONSTRAINT file_item_primarykey PRIMARY KEY(id),
    CONSTRAINT file_item_check_type CHECK(type IN ('DIRECTORY', 'FILE', 'LINK'))
)
