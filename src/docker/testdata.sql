-- Testdata for table `user`
INSERT INTO `user` (`email`, `secret_key`) VALUES
('user1@example.com', 'secret1'),
('user2@example.com', 'secret2'),
('user3@example.com', 'secret3');

-- Testdata for table `category`
INSERT INTO `category` (`name`, `user_id`) VALUES
('Category 1', 1),
('Category 2', 1),
('Category 3', 2),
('Category 4', 2),
('Category 5', 3);

-- Testdata for table `credentials`
INSERT INTO `credentials` (`username`, `password`, `url`, `email`, `notice`, `category_id`, `user_id`) VALUES
('user1', 'password1', 'https://example.com', 'user1@example.com', 'Note 1', 1, 1),
('user2', 'password2', 'https://example.com', 'user2@example.com', 'Note 2', 1, 1),
('user3', 'password3', 'https://example.com', 'user3@example.com', 'Note 3', 2, 1),
('user4', 'password4', 'https://example.com', 'user4@example.com', 'Note 4', 2, 1),
('user5', 'password5', 'https://example.com', 'user5@example.com', 'Note 5', 3, 2);
