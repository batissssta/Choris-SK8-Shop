CREATE TABLE IF NOT EXISTS `status_pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;


CREATE TABLE IF NOT EXISTS `pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `valor` double NOT NULL,
  `id_endereco` int(11) NOT NULL,
  `id_cartao` int(11) NULL,
  `id_cupom_troca` int(11) NULL,
  `id_usuario` int(11) NOT NULL,
  `id_status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_endereco` (`id_endereco`),
  KEY `id_cartao` (`id_cartao`),
  KEY `id_cupom_troca` (`id_cupom_troca`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_status` (`id_status`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `pedido`
  ADD CONSTRAINT `endereco1_ibfk_1` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id`);

ALTER TABLE `pedido`
  ADD CONSTRAINT `cartao1_ibfk_1` FOREIGN KEY (`id_cartao`) REFERENCES `cartao` (`id`);

ALTER TABLE `pedido`
  ADD CONSTRAINT `cupom1_ibfk_1` FOREIGN KEY (`id_cupom_troca`) REFERENCES `cupom_troca` (`id`);

ALTER TABLE `pedido`
  ADD CONSTRAINT `usuario1_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

ALTER TABLE `pedido`
  ADD CONSTRAINT `status_ibfk_1` FOREIGN KEY (`id_status`) REFERENCES `status_pedido` (`id`);

CREATE TABLE IF NOT EXISTS `troca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `motivo` text NULL,
  `id_pedido` int (11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pedido` (`id_pedido`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `troca`
  ADD CONSTRAINT `pedido2_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`);


CREATE TABLE IF NOT EXISTS `item_pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantidade` int(11) NOT NULL,
  `id_vestimenta` int (11) NOT NULL,
  `id_pedido` int (11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_vestimenta` (`id_vestimenta`),
  KEY `id_pedido` (`id_pedido`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `item_pedido`
  ADD CONSTRAINT `vestimenta1_ibfk_1` FOREIGN KEY (`id_vestimenta`) REFERENCES `vestimenta` (`id`);

ALTER TABLE `item_pedido`
  ADD CONSTRAINT `pedido1_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`);


CREATE TABLE IF NOT EXISTS `item_troca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantidade` int(11) NOT NULL,
  `id_vestimenta` int (11) NOT NULL,
  `id_pedido` int (11) NOT NULL,
  `id_troca` int (11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_vestimenta` (`id_vestimenta`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_troca` (`id_troca`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `item_troca`
  ADD CONSTRAINT `troca1_ibfk_1` FOREIGN KEY (`id_troca`) REFERENCES `troca` (`id`);
