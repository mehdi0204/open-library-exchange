-- Genres
INSERT INTO genre (id, name) VALUES (1, 'Roman');
INSERT INTO genre (id, name) VALUES (2, 'Science-Fiction');
INSERT INTO genre (id, name) VALUES (3, 'Fantaisie');
INSERT INTO genre (id, name) VALUES (4, 'Policier');
INSERT INTO genre (id, name) VALUES (5, 'Biographie');
INSERT INTO genre (id, name) VALUES (6, 'Histoire');
INSERT INTO genre (id, name) VALUES (7, 'Développement personnel');
INSERT INTO genre (id, name) VALUES (8, 'Manga');
INSERT INTO genre (id, name) VALUES (9, 'BD');
INSERT INTO genre (id, name) VALUES (10, 'Jeunesse');

-- Auteurs
INSERT INTO author (id, firstname, lastname) VALUES (1, 'Victor', 'Hugo');
INSERT INTO author (id, firstname, lastname) VALUES (2, 'Albert', 'Camus');
INSERT INTO author (id, firstname, lastname) VALUES (3, 'J.K.', 'Rowling');
INSERT INTO author (id, firstname, lastname) VALUES (4, 'George', 'Orwell');
INSERT INTO author (id, firstname, lastname) VALUES (5, 'Frank', 'Herbert');
INSERT INTO author (id, firstname, lastname) VALUES (6, 'Agatha', 'Christie');

-- Utilisateurs (mots de passe hashés avec bcrypt : Password1! et Admin1234!)
INSERT INTO users (id, username, email, passwordhash, active, createdat)
VALUES (1, 'Otman', 'otman@bookswap.fr', '$2a$10$8K1p/a0dR1xqM8K3qY9O8.TJCnLQjHk2lY1Qs5cZ3mN4vB6xP7W2G', true, NOW());
INSERT INTO users (id, username, email, passwordhash, active, createdat)
VALUES (2, 'aminata', 'aminata@bookswap.fr', '$2a$10$8K1p/a0dR1xqM8K3qY9O8.TJCnLQjHk2lY1Qs5cZ3mN4vB6xP7W2G', true, NOW());
INSERT INTO users (id, username, email, passwordhash, active, createdat)
VALUES (3, 'admin', 'admin@bookswap.fr', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh6.', true, NOW());

-- Rôles utilisateurs
INSERT INTO users_roles (user_id, roles) VALUES (1, 'USER');
INSERT INTO users_roles (user_id, roles) VALUES (2, 'USER');
INSERT INTO users_roles (user_id, roles) VALUES (3, 'ADMIN');
INSERT INTO users_roles (user_id, roles) VALUES (3, 'USER');

-- Livres
INSERT INTO books (id, isbn, title, description, publicationyear, createdby_id, createdat)
VALUES (1, '978-2-07-040850-4', 'Les Misérables', 'Chef-d''oeuvre de Victor Hugo', 1862, 1, NOW());
INSERT INTO books (id, isbn, title, description, publicationyear, createdby_id, createdat)
VALUES (2, '978-2-07-036024-5', 'L''Étranger', 'Roman emblématique d''Albert Camus', 1942, 1, NOW());
INSERT INTO books (id, isbn, title, description, publicationyear, createdby_id, createdat)
VALUES (3, '978-2-07-054090-6', 'Harry Potter à l''école des sorciers', 'Premier tome de la saga Harry Potter', 1997, 2, NOW());
INSERT INTO books (id, isbn, title, description, publicationyear, createdby_id, createdat)
VALUES (4, '978-2-07-036822-7', '1984', 'Roman dystopique de George Orwell', 1949, 1, NOW());
INSERT INTO books (id, isbn, title, description, publicationyear, createdby_id, createdat)
VALUES (5, '978-2-07-040507-7', 'Dune', 'Épopée de science-fiction de Frank Herbert', 1965, 2, NOW());
INSERT INTO books (id, isbn, title, description, publicationyear, createdby_id, createdat)
VALUES (6, '978-2-7023-1098-4', 'Le Meurtre de Roger Ackroyd', 'Polar d''Agatha Christie', 1926, 2, NOW());

-- Relations livres-auteurs
INSERT INTO book_authors (book_id, authors_id) VALUES (1, 1);
INSERT INTO book_authors (book_id, authors_id) VALUES (2, 2);
INSERT INTO book_authors (book_id, authors_id) VALUES (3, 3);
INSERT INTO book_authors (book_id, authors_id) VALUES (4, 4);
INSERT INTO book_authors (book_id, authors_id) VALUES (5, 5);
INSERT INTO book_authors (book_id, authors_id) VALUES (6, 6);

-- Relations livres-genres
INSERT INTO book_genres (book_id, genres_id) VALUES (1, 1);
INSERT INTO book_genres (book_id, genres_id) VALUES (2, 1);
INSERT INTO book_genres (book_id, genres_id) VALUES (3, 3);
INSERT INTO book_genres (book_id, genres_id) VALUES (4, 2);
INSERT INTO book_genres (book_id, genres_id) VALUES (5, 2);
INSERT INTO book_genres (book_id, genres_id) VALUES (6, 4);

-- Bibliothèques personnelles
-- Otman : Les Misérables (OWNED, GOOD), 1984 (OWNED, GOOD, échange), Dune (OWNED, GOOD, échange)
INSERT INTO user_books (id, user_id, book_id, status, bookcondition, availableforexchange, availableforloan, addedat)
VALUES (1, 1, 1, 'OWNED', 'GOOD', false, false, NOW());
INSERT INTO user_books (id, user_id, book_id, status, bookcondition, availableforexchange, availableforloan, addedat)
VALUES (2, 1, 4, 'OWNED', 'GOOD', true, false, NOW());
INSERT INTO user_books (id, user_id, book_id, status, bookcondition, availableforexchange, availableforloan, addedat)
VALUES (3, 1, 5, 'OWNED', 'GOOD', true, false, NOW());

-- aminata : L'Étranger (OWNED, WORN, prêt), Le Meurtre de Roger Ackroyd (OWNED, WORN, prêt), Dune (WISHLIST)
INSERT INTO user_books (id, user_id, book_id, status, bookcondition, availableforexchange, availableforloan, addedat)
VALUES (4, 2, 2, 'OWNED', 'WORN', false, true, NOW());
INSERT INTO user_books (id, user_id, book_id, status, bookcondition, availableforexchange, availableforloan, addedat)
VALUES (5, 2, 6, 'OWNED', 'WORN', false, true, NOW());
INSERT INTO user_books (id, user_id, book_id, status, bookcondition, availableforexchange, availableforloan, addedat)
VALUES (6, 2, 5, 'WISHLIST', NULL, false, false, NOW());

-- Échange : aminata demande Dune à Otman (PENDING)
INSERT INTO exchanges (id, requester_id, owner_id, userbook_id, type, status, message, requestedat)
VALUES (1, 2, 1, 3, 'EXCHANGE', 'PENDING', 'Bonjour, je souhaite échanger Dune avec toi !', NOW());

-- Avis
INSERT INTO reviews (id, user_id, book_id, rating, comment, createdat)
VALUES (1, 1, 5, 5, 'Un chef-d''oeuvre absolu de la science-fiction !', NOW());
INSERT INTO reviews (id, user_id, book_id, rating, comment, createdat)
VALUES (2, 2, 2, 4, 'Un livre marquant, Camus à son meilleur.', NOW());