CREATE TABLE IF NOT EXISTS `item_estoque` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantidade` int(11) NOT NULL,
  `data_ultima_entrada` date NULL,
  `id_vestimenta` int (11) NOT NULL,
  `data_ultima_baixa` date NULL,
  PRIMARY KEY (`id`),
  KEY `id_vestimenta` (`id_vestimenta`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `item_estoque`
  ADD CONSTRAINT `vestimenta1_ibfk_3` FOREIGN KEY (`id_vestimenta`) REFERENCES `vestimenta` (`id`);
