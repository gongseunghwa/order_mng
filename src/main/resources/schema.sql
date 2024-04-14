CREATE TABLE tb_lm_item (
                            item_id BIGINT PRIMARY KEY,
                            item_nm VARCHAR(255),
                            item_price BIGINT,
                            item_inventory BIGINT
);

CREATE TABLE tb_lm_item_order (
                            order_id UUID,
                            item_id BIGINT,
                            item_count BIGINT,
                            order_create_date TIMESTAMP
);
