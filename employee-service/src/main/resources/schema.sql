CREATE TABLE IF NOT EXISTS employee (
    employee_id   BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    email         VARCHAR(150) UNIQUE NOT NULL,
    department_id BIGINT NOT NULL,
    base_salary   NUMERIC(15,2),
    bonus         NUMERIC(15,2),
    total_salary  NUMERIC(15,2),
    project_id    BIGINT
);

CREATE SEQUENCE IF NOT EXISTS employee_id_seq START 1 INCREMENT 1;