import entities.Employee;
import entities.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("org.hibernate");
        logger.setLevel(Level.SEVERE);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        boolean exit = false;
        while (!exit) {
            printMainMenu();
            exit = mainSwitch(session);
        }


    }

    private static boolean mainSwitch(Session session) {
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                printSubMenu("employee");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        getAllEmployees(session);
                        break;
                    case 2:
                        createEmployee(session);
                        break;
                    case 3:
                        updateEmployee(session);
                        break;
                    case 4:
                        deleteEmployee(session);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid option. Please select a valid option");
                }
                break;
            case 2:
                printSubMenu("department");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        getAllDepartments(session);
                        break;
                    case 2:
                        createDepartment(session);
                        break;
                    case 3:
                        updateDepartments(session);
                        break;
                    case 4:
                        deleteDepartment(session);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid option. Please select a valid option");
                }
                break;
            case 3:
                return true;
            default:
                System.out.println("Invalid option. Please select a valid option.");
        }
        return false;
    }

    public static void getAllEmployees(Session session) {
        Query<Employee> myQuery = session.createQuery("from entities.Employee");
        List<Employee> employees = myQuery.list();
        printEmployeesHeader();

        for (Employee employee : employees ) {
            System.out.println(String.format("Employee Number: %d Name: %s Job: %s Department: %s ", employee.getEmpno(),
                    employee.getEname(), employee.getJob(), employee.getDepartment().getDname()));
        }

        System.out.println("-------------------------------------------------------------");
    }

    public static void getAllDepartments(Session session) {
        Query<Department> departmentQuery = session.createQuery("from entities.Department");
        List<Department> departments = departmentQuery.list();
        Query<Employee> employeeQuery = session.createQuery("from entities.Employee");
        List<Employee> employees = employeeQuery.list();
        printDepartmentsHeader();

        for (Department department : departments) {
            System.out.println(String.format("Department Number: %d Name: %s Location: %s", department.getDeptno(),
                    department.getDname(), department.getLoc()));
            for (Employee employee : employees) {
                if (employee.getDepartment().getDeptno() == department.getDeptno())
                    System.out.println(String.format("    - Employee Number: %d Name: %s Job: %s Department: %s ", employee.getEmpno(),
                            employee.getEname(), employee.getJob(), employee.getDepartment().getDname()));
            }
            System.out.println();
        }

        System.out.println("-------------------------------------------------------------");
    }

    public static void createEmployee(Session session) {
        Scanner scanner = new Scanner(System.in);
        printEmployeesHeader();
        System.out.print("Employee number: ");
        int empno = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine().toUpperCase();
        System.out.print("Job: ");
        String job = scanner.nextLine().toUpperCase();
        System.out.print("Department number: ");
        int deptno = scanner.nextInt();
        scanner.nextLine();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Employee employee = new Employee();
            Query<Department> departmentQuery = session.createQuery("from entities.Department where deptno = :deptNo", Department.class);
            departmentQuery.setParameter("deptNo", deptno);
            Department dept = departmentQuery.uniqueResult();

            Query<Employee> employeeQuery = session.createQuery("from entities.Employee where empno = :empNo", Employee.class);
            employeeQuery.setParameter("empNo", empno);
            Employee employeeBBDD = employeeQuery.uniqueResult();

            if (dept != null && employeeBBDD == null) {
                employee.setEmpno(empno);
                employee.setEname(name);
                employee.setJob(job);

                employee.setDepartment(dept);

                session.save(employee);
                transaction.commit();
            }
            else if (employeeBBDD != null) {
                System.out.println("Employee informed already exists.");
                transaction.rollback();
            }
            else {
                System.out.println("Department not found.");
                transaction.rollback();
            }

        }
        catch( Exception e ) {
            if (transaction != null)
                transaction.rollback();
            System.out.println( e.getMessage() );
        }
    }

    public static void createDepartment(Session session) {
        Scanner scanner = new Scanner(System.in);
        printEmployeesHeader();
        System.out.print("Department number: ");
        int deptno = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine().toUpperCase();
        System.out.print("Location: ");
        String location = scanner.nextLine().toUpperCase();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Department department = new Department();
            Query<Department> query = session.createQuery("from entities.Department where deptno = :deptNo", Department.class);
            query.setParameter("deptNo", deptno);
            Department dept = query.uniqueResult();

            if (dept == null) {
                department.setDeptno(deptno);
                department.setDname(name);
                department.setLoc(location);

                session.save(department);
                transaction.commit();
            }
            else {
                System.out.println("Department informed already exists.");
                transaction.rollback();
            }

        }
        catch( Exception e ) {
            if (transaction != null)
                transaction.rollback();
            System.out.println( e.getMessage() );
        }
    }

    public static void updateEmployee(Session session) {
        try {
            Scanner scanner = new Scanner(System.in);
            printEmployeesHeader();
            System.out.print("Write the employee number to update: ");
            int empno = scanner.nextInt();
            scanner.nextLine();

            Query<Employee> query = session.createQuery("from entities.Employee where empno = :empNo", Employee.class);
            query.setParameter("empNo", empno);
            Employee employee = query.uniqueResult();
            if (employee != null) {
                session.beginTransaction();

                System.out.println("Employee found. Now you can change the name, job and department. (don't write nothing to not update the value)");
                System.out.print("Name: ");
                String name = scanner.nextLine().toUpperCase();
                if (!name.isEmpty())
                    employee.setEname(name);

                System.out.print("Job: ");
                String job = scanner.nextLine().toUpperCase();
                if (!job.isEmpty())
                    employee.setJob(job);

                System.out.print("Department number: ");
                String deptString = scanner.nextLine();
                if (!deptString.isEmpty()) {
                    int deptno = Integer.parseInt(deptString);
                    Query<Department> queryDept = session.createQuery("from entities.Department where deptno = :deptNo", Department.class);
                    queryDept.setParameter("deptNo", deptno);
                    Department dept = queryDept.uniqueResult();
                    if (dept != null) {
                        employee.setDepartment(dept);
                    } else {
                        System.out.println("Department not found.");
                    }
                }

                session.getTransaction().commit();
            } else
                System.out.println("Employee not found.");


        }
        catch( Exception e ) {
            if (session != null)
                session.getTransaction().rollback();
            System.out.println( e.getMessage() );
        }
    }

    public static void updateDepartments(Session session) {
        try {
            Scanner scanner = new Scanner(System.in);
            printDepartmentsHeader();
            System.out.print("Write the department number to update: ");
            int deptno = scanner.nextInt();
            scanner.nextLine();

            Query<Department> query = session.createQuery("from entities.Department where deptno = :deptNo", Department.class);
            query.setParameter("deptNo", deptno);
            Department department = query.uniqueResult();
            if (department != null) {
                session.beginTransaction();

                System.out.println("Department found. Now you can change the name and location (don't write nothing to not update the value)");
                System.out.print("Name: ");
                String name = scanner.nextLine().toUpperCase();
                if (!name.isEmpty())
                    department.setDname(name);

                System.out.print("Location: ");
                String location = scanner.nextLine().toUpperCase();
                if (!location.isEmpty())
                    department.setLoc(location);

                session.getTransaction().commit();
            } else
                System.out.println("Department not found.");
        }
        catch( Exception e ) {
            if (session != null)
                session.getTransaction().rollback();
            System.out.println( e.getMessage() );
        }
    }

    public static void deleteEmployee(Session session) {
        try {
            Scanner scanner = new Scanner(System.in);
            printEmployeesHeader();
            System.out.print("Write the employee number to delete: ");
            int empno = scanner.nextInt();
            scanner.nextLine();

            Query<Employee> query = session.createQuery("from entities.Employee where empno = :empNo", Employee.class);
            query.setParameter("empNo", empno);
            Employee employee = query.uniqueResult();
            if (employee != null) {
                session.beginTransaction();

                System.out.print("Employee found. Do you want to delete it? (Y/N): ");
                String option = scanner.nextLine().toUpperCase();
                if (option.equalsIgnoreCase("Y")){
                    session.delete(employee);
                    session.getTransaction().commit();
                    System.out.println("Employee with number " + empno + " has been deleted.");
                }

            } else
                System.out.println("Employee not found.");
        }
        catch( Exception e ) {
            if (session != null)
                session.getTransaction().rollback();
            System.out.println( e.getMessage() );
        }
    }

    public static void deleteDepartment(Session session) {
        try {
            Scanner scanner = new Scanner(System.in);
            printDepartmentsHeader();
            System.out.print("Write the department number to delete: ");
            int deptno = scanner.nextInt();
            scanner.nextLine();

            Query<Department> departmentQuery = session.createQuery("from entities.Department where deptno = :deptNo", Department.class);
            departmentQuery.setParameter("deptNo", deptno);
            Department department = departmentQuery.uniqueResult();
            if (department != null) {
                session.beginTransaction();

                System.out.print("Department found. Do you want to delete it? (Y/N): ");
                String option = scanner.nextLine().toUpperCase();
                if (option.equalsIgnoreCase("Y")){
                    Query<Employee> employeeQuery = session.createQuery("from entities.Employee where deptno = :deptNo", Employee.class);
                    employeeQuery.setParameter("deptNo", department.getDeptno());
                    List<Employee> employees = employeeQuery.list();
                    System.out.println("If you delete the " + department.getDname() + " department you will delete also " +
                                        employees.size() + " employees. Do you want to continue with the process? (Y/N)");
                    option = scanner.nextLine();
                    if (option.equalsIgnoreCase("Y")) {
                        for (Employee employee: employees) {
                            session.delete(employee);
                        }

                        session.delete(department);
                        session.getTransaction().commit();

                        System.out.println("Department with number " + deptno + " has been deleted along with its employees.");
                    }
                    else {
                        System.out.println("Delete cancelled.");
                    }
                }
                else
                    System.out.println("Delete cancelled.");

            } else
                System.out.println("Department not found.");
        }
        catch( Exception e ) {
            if (session != null)
                session.getTransaction().rollback();
            System.out.println( e.getMessage() );
        }
    }

    public static void printMainMenu() {
        System.out.println("-------------------------------------");
        System.out.println("----- EMPLOYEES AND DEPARTMENTS -----");
        System.out.println("-------------------------------------");
        System.out.println("1. Employees");
        System.out.println("2. Departments");
        System.out.println("3. Exit");
        System.out.print("Choose and option: ");
    }

    public static void printSubMenu(String type) {
        if (type.equalsIgnoreCase("EMPLOYEE"))
            printEmployeesHeader();
        else
            printDepartmentsHeader();

        System.out.println("1. Show all " + type + "s");
        System.out.println("2. Create a " + type);
        System.out.println("3. Update a " + type);
        System.out.println("4. Delete a " + type);
        System.out.println("5. Exit");
        System.out.print("Choose and option: ");
    }

    public static void printEmployeesHeader() {
        System.out.println("-----------------------------------");
        System.out.println("------------ EMPLOYEES ------------");
        System.out.println("-----------------------------------");
    }
    public static void printDepartmentsHeader() {
        System.out.println("------------------------------------");
        System.out.println("------------ DEPARTMENT ------------");
        System.out.println("------------------------------------");
    }
}
