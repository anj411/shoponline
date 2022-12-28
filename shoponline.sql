create database shoponline;

INSERT INTO `shoponline`.`role` (`role_id`, `role_name`) VALUES ('1', 'Admin');
INSERT INTO `shoponline`.`role` (`role_id`, `role_name`) VALUES ('2', 'User');


INSERT INTO `shoponline`.`user`
(`user_id`,
`user_address`,
`user_email`,
`user_first_name`,
`user_last_name`,
`user_password`)
VALUES
('1', NULL, 'admin123@gmail.com', 'admin', 'admin', '$2a$10$u7aUcr0nJbCLdvADUxp/ruXGfjwgAB4yljqqDYKhkNiMF/NRSsT4C');

INSERT INTO `shoponline`.`user_role` (`user_id`, `role_id`) VALUES ('1', '1');
