CREATE TABLE IF NOT EXISTS `categoria_vestimenta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;


CREATE TABLE IF NOT EXISTS `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;


CREATE TABLE IF NOT EXISTS `vestimenta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(11) NOT NULL,
  `id_marca` int(11) NOT NULL,
  `cor` varchar(50) NOT NULL,
  `tamanho` varchar(50) NOT NULL,
  `genero` varchar(30) NOT NULL,
  `valor_venda` double NOT NULL,
  `imagem` longtext,
  PRIMARY KEY (`id`),
  KEY `id_categoria` (`id_categoria`,`id_marca`),
  KEY `id_marca` (`id_marca`)
  ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

ALTER TABLE `vestimenta`
  ADD CONSTRAINT `vestimenta_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria_vestimenta` (`id`),
  ADD CONSTRAINT `vestimenta_ibfk_2` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id`);
