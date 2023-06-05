-- Insert test data for `user` table
INSERT INTO `onepass`.`user` (`iduser`, `email`, `secret_key`) VALUES
(1, 'user1@example.com', 'secret1'),
(2, 'user2@example.com', 'secret2'),
(3, 'user3@example.com', 'secret3');

-- Insert test data for `category` table
INSERT INTO `onepass`.`category` (`id`, `name`, `user_iduser`) VALUES
(1, 'Category 1', 1),
(2, 'Category 2', 1),
(3, 'Category 3', 2),
(4, 'Category 4', 3);

-- Insert test data for `credentials` table
INSERT INTO `onepass`.`credentials` (`id`, `username`, `password`, `url`, `email`, `notice`, `category_id`) VALUES
(1, 'user1', 'password1', 'example.com', 'user1@example.com', 'Notice 1', 1),
(2, 'user2', 'password2', 'example.com', 'user2@example.com', 'Notice 2', 1),
(3, 'user3', 'password3', 'example.com', 'user3@example.com', 'Notice 3', 2),
(4, 'user4', 'password4', 'example.com', 'user4@example.com', 'Notice 4', 3),
(5, 'user5', 'password5', 'example.com', 'user5@example.com', 'Notice 5', 3);
