CREATE OR REPLACE FUNCTION employeebydept (indept text)
RETURNS SETOF employee
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    RETURN QUERY
    SELECT e.* FROM public.employee e
	INNER JOIN public.dept d ON e.deptno = d.deptno
    WHERE public.dept.dname = indept;
END;
$BODY$;

SELECT * FROM employeebydept ('ACCOUNTING')