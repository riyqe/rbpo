INSERT INTO users (username, email, department)
VALUES
    ('ivan.ivanov', 'i.ivanov@corp.com', 'IT Support'),
    ('alex.petrov', 'a.petrov@corp.com', 'Finance'),
    ('olga.smirnova', 'o.smirnova@corp.com', 'HR');

INSERT INTO executors (name, specialization, email, department)
VALUES
    ('Dmitry Orlov', 'VPN', 'd.orlov@corp.com', 'Network'),
    ('Svetlana Kozlova', 'Licenses', 's.kozlova@corp.com', 'Software'),
    ('Ivan Grigoryev', 'Hardware', 'i.grigoryev@corp.com', 'Tech');

INSERT INTO categories (name, description)
VALUES
    ('Инциденты', 'Неисправности оборудования или ПО'),
    ('Лицензии и софт', 'Проблемы с активацией или установкой программ'),
    ('Сеть и VPN', 'Ошибки подключения к корпоративной сети');

INSERT INTO sla (level, response_hours, resolve_hours)
VALUES
    ('Обычный', 8, 24),
    ('Средний', 4, 12),
    ('Критический', 1, 4);

INSERT INTO tickets (
    title, description, status, user_id, executor_id, category_id, sla_id,
    created_at, updated_at, due_date, resolution
)
VALUES
    (
        'VPN не подключается',
        'Пользователь сообщает, что VPN не подключается из дома.',
        'IN_PROGRESS',
        1,  -- user: m.ivanova
        1,  -- executor: Dmitry Orlov
        3,  -- category: Сеть и VPN
        2,  -- SLA: Средний
        NOW(), NOW(), NOW() + INTERVAL '12 hours',
        NULL
    ),
    (
        'Ошибка активации MS Office',
        'Не удается активировать лицензию Office 365 на новом ПК.',
        'CREATED',
        2,
        2,
        2,
        1,
        NOW(), NULL, NOW() + INTERVAL '1 day',
        NULL
    ),
    (
        'Не работает монитор',
        'Монитор не включается после замены блока питания.',
        'RESOLVED',
        3,
        3,
        1,
        3,
        NOW() - INTERVAL '2 days', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day',
        'Заменен кабель питания'
    );
