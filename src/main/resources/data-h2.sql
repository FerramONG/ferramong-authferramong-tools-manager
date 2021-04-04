-- Initialize Dwellers
set @john_doe = 1;
set @jane_doe = 2;

insert into dwellers (id, name, cpf) values
    (@john_doe, 'John Doe', '000-000-000.00'),
    (@jane_doe, 'Jane Doe', '000-000-000.00');

-- Initialize Tools
set @category = 'All';
set @description = 'What is this tool for?';
set @instructions = 'How to use this tool?';
set @from_date = current_date();
set @to_date = dateAdd(day, 7, current_date());

insert into tools (id, name, category, description, instructions, price, available_from, available_until, owner_id)
values
    ( 0, 'Hammer 0', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 1, 'Hammer 1', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 2, 'Hammer 2', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 3, 'Hammer 3', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 4, 'Hammer 4', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 5, 'Hammer 5', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 6, 'Hammer 6', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 7, 'Hammer 7', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 8, 'Hammer 8', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    ( 9, 'Hammer 9', @category, @description, @instructions, 100, @from_date, @to_date, @john_doe),
    (10,    'Saw 0', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (11,    'Saw 1', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (12,    'Saw 2', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (13,    'Saw 3', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (14,    'Saw 4', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (15,    'Saw 5', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (16,    'Saw 6', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (17,    'Saw 7', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (18,    'Saw 8', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe),
    (19,    'Saw 9', @category, @description, @instructions, 100, @from_date, @to_date, @jane_doe);
