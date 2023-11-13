ALTER TABLE budget_by_category
    ADD COLUMN budget_id bigint NOT NULL,
    ADD CONSTRAINT fk_budget_id FOREIGN KEY (budget_id) REFERENCES budget (id);
