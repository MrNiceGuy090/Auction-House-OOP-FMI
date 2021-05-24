CREATE DATABASE `PAO`;
USE `PAO`;

/*DROP VIEW Engines;*/
CREATE TABLE `Engines` (
`id` varchar(36) NOT NULL,
`horsePower` double NOT NULL,
`isElectric` boolean NOT NULL,
`capacity` double NOT NULL,
PRIMARY KEY (`id`));

/*DROP TABLE Products;*/
CREATE TABLE `Products` (
`id` varchar(36) NOT NULL,
`name` varchar(20) NOT NULL,
`brand` varchar(20) NOT NULL,
`builtDate` int(11) NOT NULL,
PRIMARY KEY (`id`)
);

/*DROP TABLE Cars;*/
CREATE TABLE `Cars` (
`id` varchar(36) NOT NULL,
`nrOfSeats` int(5) NOT NULL,
`isAutomatic` boolean NOT NULL,
`engine` varchar(36) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`engine`) REFERENCES Engines(id) ON DELETE CASCADE,
FOREIGN KEY (`id`) REFERENCES Products(id) ON DELETE CASCADE);

/*DROP TABLE Trucks;*/
CREATE TABLE `Trucks` (
`id` varchar(36) NOT NULL,
`capacity` int(11) NOT NULL,
`noOfWheels` int(11) NOT NULL,
`engine` varchar(36) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`engine`) REFERENCES Engines(id) ON DELETE CASCADE,
FOREIGN KEY (`id`) REFERENCES Products(id) ON DELETE CASCADE);

/*DROP TABLE Motorcycles;*/
CREATE TABLE `Motorcycles` (
`id` varchar(36) NOT NULL,
`category` varchar(20) NOT NULL,
`engine` varchar(36) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`engine`) REFERENCES Engines(id) ON DELETE CASCADE, 
FOREIGN KEY (`id`) REFERENCES Products(id) ON DELETE CASCADE);

/*DROP TABLE Bicycles;*/
CREATE TABLE `Bicycles` (
`id` varchar(36) NOT NULL,
`type` varchar(20) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`id`) REFERENCES Products(id) ON DELETE CASCADE);

/*DROP TABLE Users;*/
CREATE TABLE `Users` (
`id` varchar(36) NOT NULL,
`name` varchar(20) NOT NULL,
`password` varchar(20) NOT NULL,
`isBanned` boolean NOT NULL,
PRIMARY KEY (`id`));

/*DROP TABLE Bids;*/
CREATE TABLE `Bids` (
`id` varchar(36) NOT NULL,
`bidder` varchar(36) NOT NULL,
`price` int(11) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`bidder`) REFERENCES Users(id) ON DELETE CASCADE
);

/*DROP TABLE Auctions;*/
CREATE TABLE `Auctions` (
`id` varchar(36) NOT NULL,
`product` varchar(36) NOT NULL,
`highestBid` varchar(36) NOT NULL,
`owner` varchar(36) NOT NULL,
`isActive` boolean NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`product`) REFERENCES Products(id) ON DELETE CASCADE,
FOREIGN KEY (`highestBid`) REFERENCES Bids(id) ON DELETE CASCADE,
FOREIGN KEY (`owner`) REFERENCES Users(id) ON DELETE CASCADE
);

