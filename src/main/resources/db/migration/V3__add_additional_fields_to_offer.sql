ALTER TABLE offer
    ADD COLUMN if not exists minimum_spend decimal(12,2);

ALTER TABLE offer
    ADD COLUMN if not exists discount_percentage int;

ALTER TABLE offer
    ADD COLUMN if not exists offer_code text;
