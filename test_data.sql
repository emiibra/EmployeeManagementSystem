-- ======== EMPLOYEES ==========
INSERT INTO employees (empid, Fname, Lname, email, HireDate, Salary, SSN)
VALUES
(101, 'Alice', 'Kim', 'alice.kim@company.com', '2022-01-15', 55000.00, '123456789'),
(102, 'Brian', 'Lopez', 'brian.lopez@company.com', '2021-03-22', 72000.00, '987654321'),
(103, 'Cindy', 'Patel', 'cindy.patel@company.com', '2020-06-10', 90000.00, '555444333'),
(104, 'David', 'Yang', 'david.yang@company.com', '2019-02-05', 120000.00, '111222333');

-- ======== DIVISIONS ==========
INSERT INTO division (ID, Name, city, addressLine1, addressLine2, state, country, postalCode)
VALUES
(1, 'Engineering', 'Miami', '100 Main St', '', 'FL', 'USA', '33101'),
(2, 'Finance', 'Miami', '200 Money Rd', '', 'FL', 'USA', '33102'),
(3, 'HR', 'Orlando', '300 People Ave', '', 'FL', 'USA', '32801');

-- ===== EMPLOYEE → DIVISION =====
INSERT INTO employee_division (empid, div_ID)
VALUES
(101, 1),
(102, 1),
(103, 2),
(104, 3);

-- ======== JOB TITLES ==========
INSERT INTO job_titles (job_title_id, job_title)
VALUES
(1, 'Software Engineer'),
(2, 'Senior Engineer'),
(3, 'Accountant'),
(4, 'HR Specialist'),
(5, 'Engineering Manager');

-- ===== EMPLOYEE → JOB TITLES =====
INSERT INTO employee_job_titles (empid, job_title_id)
VALUES
(101, 1),
(102, 2),
(103, 3),
(104, 4);

-- ======== PAYROLL (Jan 2024) ==========
INSERT INTO payroll
(payID, pay_date, earnings, fed_tax, fed_med, fed_SS, state_tax, retire_401k, health_care, empid)
VALUES
(1001, '2024-01-15', 3500.00, 300, 50, 150, 100, 75, 200, 101),
(1002, '2024-01-15', 4200.00, 350, 60, 180, 120, 100, 250, 102),
(1003, '2024-01-15', 4800.00, 400, 70, 210, 140, 125, 260, 103),
(1004, '2024-01-15', 6200.00, 500, 90, 300, 160, 180, 300, 104);

-- ======== PAYROLL (Feb 2024) ==========
INSERT INTO payroll
(payID, pay_date, earnings, fed_tax, fed_med, fed_SS, state_tax, retire_401k, health_care, empid)
VALUES
(1005, '2024-02-15', 3600.00, 305, 52, 152, 105, 80, 210, 101),
(1006, '2024-02-15', 4250.00, 360, 62, 182, 125, 105, 260, 102),
(1007, '2024-02-15', 4900.00, 420, 72, 215, 150, 130, 280, 103),
(1008, '2024-02-15', 6300.00, 520, 92, 310, 165, 190, 325, 104);

-- ======== PAYROLL (Mar 2024) ==========
INSERT INTO payroll
(payID, pay_date, earnings, fed_tax, fed_med, fed_SS, state_tax, retire_401k, health_care, empid)
VALUES
(1009, '2024-03-15', 3650.00, 310, 53, 155, 110, 84, 215, 101),
(1010, '2024-03-15', 4300.00, 365, 63, 185, 130, 110, 265, 102),
(1011, '2024-03-15', 4950.00, 430, 75, 220, 155, 135, 290, 103),
(1012, '2024-03-15', 6400.00, 530, 95, 320, 170, 200, 335, 104);

