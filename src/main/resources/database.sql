-- =========================
-- CLICK APPS (DEFAULT FEATURES)
-- =========================

INSERT INTO click_apps (id, name)
VALUES (gen_random_uuid(), 'TIME_TRACKING'),
       (gen_random_uuid(), 'PRIORITY'),
       (gen_random_uuid(), 'TAGS'),
       (gen_random_uuid(), 'DEPENDENCY'),
       (gen_random_uuid(), 'CHECKLIST'),
       (gen_random_uuid(), 'COMMENTS'),
       (gen_random_uuid(), 'ATTACHMENTS');

-- =========================
-- VIEWS (DEFAULT)
-- =========================

INSERT INTO view (id, name)
VALUES (gen_random_uuid(), 'LIST'),
       (gen_random_uuid(), 'BOARD'),
       (gen_random_uuid(), 'CALENDAR'),
       (gen_random_uuid(), 'GANTT');