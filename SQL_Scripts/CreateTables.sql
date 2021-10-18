start transaction;

create user if not exists 'admin'@'localhost' identified by 'password';
grant all privileges on *.* to 'admin'@'localhost';

use cacyno;

create table game(
id varchar(50) NOT NULL,
user_id varchar(10) NOT NULL,
isSpectator boolean,
primary key (id),
foreign key (user_id) references user(id) ON DELETE CASCADE
);
create table current_games_list(
game_id varchar(50),
primary key(game_id),
foreign key(game_id) references game(id) ON DELETE CASCADE
);

create table players(
game_id varchar(50), 
user_id varchar(50),
current_game_money int,
card1_num int, 
card1_suit int,
card2_num int, 
card2_suit int,
folded boolean,
foreign key (user_id) references user(id)ON DELETE CASCADE,
foreign key (game_id) references game(id) ON DELETE CASCADE
);

create table deck(
game_id varchar(50),
cards_num int,
cards_suit int,
foreign key (game_id) references game(id)
);

create table public_cards(
game_id varchar(50),
card1_num int, 
card1_suit int, 
card2_num int, 
card2_suit int,
card3_num int, 
card3_suit int,
card4_num int, 
card4_suit int,
card5_num int, 
card5_suit int,
pot int, 
foreign key (game_id) references game(id) ON DELETE CASCADE
);