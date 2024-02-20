create table if not exists sponsor(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    industry varchar(255)
);

create table if not exists event(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
   eventDate varchar(255)
);

create table if not exists event_sponsor(
    sponsorId INT ,
    eventId INT ,
    PRIMARY KEY(sponsorId,eventId),
    FOREIGN KEY(sponsorId) REFERENCES sponsor(id),
    FOREIGn KEY(eventId) REFERENCES event(id)
);