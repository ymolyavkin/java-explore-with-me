create TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(250) NOT NULL,
                                     email VARCHAR(254) NOT NULL,
                                     CONSTRAINT PK_USER PRIMARY KEY (id),
                                     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);
create TABLE IF NOT EXISTS categories (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(50) NOT NULL,
                                     CONSTRAINT PK_CATEGORY PRIMARY KEY (id)
);
create TABLE IF NOT EXISTS locations (
                                    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                    lat REAL NOT NULL,
                                    lon REAL NOT NULL,
                                    CONSTRAINT PK_LOCATION PRIMARY KEY (id)
);
create TABLE IF NOT EXISTS events (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     annotation VARCHAR(2000) NOT NULL,
                                     category_id BIGINT NOT NULL,
                                     description VARCHAR(7000) NOT NULL,
                                     event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                     location_id BIGINT NOT NULL,
                                     paid BOOLEAN NOT NULL,--TRUE, FALSE
                                     participant_limit INT,
                                     request_moderation BOOLEAN,--TRUE, FALSE
                                     title VARCHAR(120) NOT NULL,
                                     eventsState VARCHAR(9), --PENDING, CANCELED, PUBLISHED
                                     created TIMESTAMP WITHOUT TIME ZONE,
                                     initiator_id BIGINT NOT NULL,
                                     published_on TIMESTAMP WITHOUT TIME ZONE,

                                     CONSTRAINT PK_EVENT PRIMARY KEY (id),
                                     CONSTRAINT FK_CATEGORY_ID FOREIGN KEY(category_id) REFERENCES categories(id),
                                     CONSTRAINT FK_INITIATOR_ID FOREIGN KEY(initiator_id) REFERENCES users(id),
                                     CONSTRAINT FK_LOCATION_ID FOREIGN KEY(location_id) REFERENCES locations(id)
);
create TABLE IF NOT EXISTS requests (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     created TIMESTAMP WITHOUT TIME ZONE,
                                     event_id BIGINT NOT NULL,
                                     requester_id BIGINT NOT NULL,
                                     status VARCHAR(9), --PENDING, CANCELED, PUBLISHED

                                     CONSTRAINT PK_REQUEST PRIMARY KEY (id),
                                     CONSTRAINT FK_EVENT_ID FOREIGN KEY(event_id) REFERENCES events(id),
                                     CONSTRAINT FK_REQUESTER_ID FOREIGN KEY(requester_id) REFERENCES users(id)
);
create TABLE IF NOT EXISTS compilations (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     pinned BOOLEAN, --TRUE, FALSE
                                     title VARCHAR(50),

                                     CONSTRAINT PK_COMPILATION PRIMARY KEY (id)
);
create TABLE IF NOT EXISTS events_compilations (
                                    compilation_id BIGINT REFERENCES compilations (id),
                                    event_id       BIGINT REFERENCES events (id),
                                    CONSTRAINT PK_EVENT_COMPILATION  PRIMARY KEY (compilation_id, event_id)
);
