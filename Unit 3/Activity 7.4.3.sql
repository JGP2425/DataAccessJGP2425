CREATE OR REPLACE FUNCTION employeebynameinpattern (pattern text)
RETURNS SETOF employee
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    RETURN QUERY
    SELECT * FROM public.employee
	WHERE ename LIKE '%' + pattern + '%';
END;
$BODY$;

SELECT * FROM employeebynameinpattern('CLA')
SELECT * FROM employeebynameinpattern('L')