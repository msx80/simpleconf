 ![CI](https://github.com/msx80/simpleconf/actions/workflows/maven.yml/badge.svg) [![JitPack](https://jitpack.io/v/msx80/simpleconf.svg)](https://jitpack.io/#msx80/simpleconf)
# simpleconf
Very simple configuration handling library


Basic usage:

    Configuration c = Configuration.load();
    
    String myValue = c.get("my.value");
    BigDecimal max = c.getBigDecimal("my.stuff.max", BigDecimal.ONE);

