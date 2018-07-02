
    drop table if exists customer;

    drop table if exists hibernate_sequence;

    drop table if exists item;

    drop table if exists order_detail;

    drop table if exists order_table;

    drop table if exists shipping_address;

    create table customer (
       id bigint not null auto_increment,
        customer_id varchar(255) not null,
        customer_pwd varchar(255) not null,
        primary key (id)
    ) type=MyISAM;

    create table hibernate_sequence (
       next_val bigint
    ) type=MyISAM;

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    create table item (
       item_id bigint not null,
        item_name varchar(255) not null,
        item_price double precision not null,
        primary key (item_id)
    ) type=MyISAM;

    create table order_detail (
       order_detail_id bigint not null,
        item_sub_total double precision not null,
        quantity integer not null,
        item_id bigint not null,
        order_id bigint not null,
        primary key (order_detail_id)
    ) type=MyISAM;

    create table order_table (
       order_id bigint not null auto_increment,
        order_confirmed bit not null,
        order_date datetime,
        order_number varchar(255),
        order_total_cost double precision,
        customer_id bigint not null,
        shipping_id bigint,
        primary key (order_id)
    ) type=MyISAM;

    create table shipping_address (
       shipping_id bigint not null,
        shipping_address varchar(255) not null,
        customer_id bigint not null,
        primary key (shipping_id)
    ) type=MyISAM;

    alter table order_detail 
       add constraint FK4dtqbi7ilse9x730y087wagm2 
       foreign key (item_id) 
       references item (item_id);

    alter table order_detail 
       add constraint FK15634ut62dpm7cmpeltva15k 
       foreign key (order_id) 
       references order_table (order_id);

    alter table order_table 
       add constraint FKdit09e676nqbguvt1k1w8mlj2 
       foreign key (customer_id) 
       references customer (id);

    alter table order_table 
       add constraint FKabhsoq1565ag7kcgc2ax6dsaq 
       foreign key (shipping_id) 
       references shipping_address (shipping_id);

    alter table shipping_address 
       add constraint FKlfjcq5g9h0ry4gbhvgr0kxfm6 
       foreign key (customer_id) 
       references customer (id);
