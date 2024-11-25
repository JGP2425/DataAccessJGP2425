CREATE OR REPLACE FUNCTION is_product_discounted_in_period(
    product_id_input INT,
    start_date_input DATE,
    end_date_input DATE
) RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS (
        SELECT 1
        FROM seller_products
        WHERE product_id = product_id_input
          AND offer_start_date <= end_date_input
          AND offer_end_date >= start_date_input
    );
END;
$$ LANGUAGE plpgsql;