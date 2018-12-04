CREATE TABLE rooms (
    id bigint NOT NULL,
        nightly_price double precision NOT NULL,
        number_places integer NOT NULL,
        room_number integer NOT NULL,
        CONSTRAINT rooms_pkey PRIMARY KEY (id)
);

CREATE TABLE services
(
    id bigint NOT NULL,
    service_name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    service_price double precision NOT NULL,
    service_duration integer NOT NULL,
    CONSTRAINT services_pkey PRIMARY KEY (id)
);

CREATE TABLE clients
(
    id bigint NOT NULL,
    name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    forename character varying(200) COLLATE pg_catalog."default" NOT NULL,
    check_in timestamp NOT NULL,
    check_out timestamp NOT NULL,
    cnp character varying(50) COLLATE pg_catalog."default" NOT NULL,
    street character varying(200) COLLATE pg_catalog."default" NOT NULL,
    street_number integer NOT NULL,
    birthday timestamp NOT NULL,
    type_id character varying(10) COLLATE pg_catalog."default" NOT NULL,
    series_id character varying(10) COLLATE pg_catalog."default" NOT NULL,
    number_id character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT clients_pkey PRIMARY KEY (id)
);

CREATE TABLE bookedrooms
(
    id bigint NOT NULL,
    total_price double precision NOT NULL,
    client_id bigint,
    room_id bigint,
    CONSTRAINT bookedrooms_pkey PRIMARY KEY (id),
    CONSTRAINT bookedrooms_clientid_fkey FOREIGN KEY (client_id)
        REFERENCES public.clients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT bookedrooms_roomid_fkey FOREIGN KEY (room_id)
        REFERENCES public.rooms (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE bookedrooms_services
(
    bookedroom_id bigint NOT NULL,
    service_id bigint NOT NULL,
    CONSTRAINT bookedrooms_services_bookedroomid_fkey FOREIGN KEY (bookedroom_id)
        REFERENCES public.bookedrooms (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT bookedrooms_services_serviceid_fkey FOREIGN KEY (service_id)
        REFERENCES public.services (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE bookedrooms ADD total_service_prices double precision;
ALTER TABLE clients ADD duration bigint;
ALTER TABLE bookedrooms DROP COLUMN total_price;
ALTER TABLE bookedrooms DROP COLUMN total_service_prices;
ALTER TABLE clients DROP COLUMN check_in;
ALTER TABLE clients DROP COLUMN check_out;
ALTER TABLE bookedrooms ADD check_in timestamp;
ALTER TABLE bookedrooms ADD check_out timestamp;