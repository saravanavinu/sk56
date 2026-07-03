CREATE TABLE Employee (emp_id INT PRIMARY KEY,emp_name VARCHAR(100),salary DECIMAL(10,2));

DELIMITER $$
CREATE TRIGGER trg_before_insert_employee
BEFORE INSERT ON Employee
FOR EACH ROW
BEGIN
    IF NEW.salary < 10000 THEN
        SET NEW.salary = 10000;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER trg_before_update_employee
BEFORE UPDATE ON Employee
FOR EACH ROW
BEGIN
    IF NEW.salary < 10000 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Salary cannot be less than 10000';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE Employee_Menu(IN ch INT,IN p_emp_id INT,N p_emp_name VARCHAR(100),IN p_salary DECIMAL(10,2))
BEGIN
    IF ch = 1 THEN
        INSERT INTO Employee(emp_id, emp_name, salary)
        VALUES(p_emp_id, p_emp_name, p_salary);
        SELECT 'Employee Inserted Successfully' AS Message;
    ELSEIF ch = 2 THEN
        UPDATE Employee
        SET salary = p_salary
        WHERE emp_id = p_emp_id;
        SELECT 'Salary Updated Successfully' AS Message;
    ELSEIF ch = 3 THEN
        DELETE FROM Employee
        WHERE emp_id = p_emp_id;
        SELECT 'Employee Deleted Successfully' AS Message;
    ELSEIF ch = 4 THEN
        SELECT * FROM Employee;
    ELSEIF ch = 5 THEN
        SELECT 'Program Exited' AS Message;
    ELSE
        SELECT 'Invalid Choice' AS Message;
    END IF;
END$$
DELIMITER ;

CALL Employee_Menu(1,101,'Saravana',8000);

CALL Employee_Menu(4,0,'',0);

CALL Employee_Menu(2,101,'',15000);

CALL Employee_Menu(2,101,'',9000);

CALL Employee_Menu(3,101,'',0);

CALL Employee_Menu(5,0,'',0);