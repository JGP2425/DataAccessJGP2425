CREATE OR REPLACE FUNCTION employeebyjob (injob text)
RETURNS SETOF employee
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    RETURN QUERY
    SELECT * FROM public.employee
    WHERE job = injob;
END;
$BODY$;

SELECT * FROM employeebyjob('SALESMAN')