package com.castillo.utils;


import com.castillo.utils.pojos.Computer;
import com.castillo.utils.pojos.SoundCard;
import com.castillo.utils.pojos.USB;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;


import static com.castillo.utils.NullSafe.of;
import static org.junit.jupiter.api.Assertions.*;

class NullSafeTest {

    @Test
    void orGet() {
        final String defaultValue = "default";
        String value = "test";
        Supplier<String> defaultSupplier = ()-> defaultValue;
        assertEquals(value, NullSafe.orGet(value, defaultSupplier));
        assertEquals(defaultValue, NullSafe.orGet(null, defaultSupplier));
    }

    @Test
    void nullSafe(){
        Computer computer = null;
        NullSafe<USB> usbNullSafe = of(computer)
                .get(Computer::getSoundCard)
                .get(SoundCard::getUsb);

        USB usb = usbNullSafe.value();
        assertNull(usb);
//

        String  version = of(computer)
                .get(Computer::getSoundCard)
                .get(SoundCard::getUsb)
                .get(USB::getVersion)
                .or("OR");
        assertEquals("OR", version);
        computer = new Computer();
        version = of(computer)
                .get(Computer::getSoundCard)
                .get(SoundCard::getUsb)
                .get(USB::getVersion).value();

        assertNull(version);

        computer = new Computer();
        computer.setSoundCard(new SoundCard());
        version = of(computer)
                .get(Computer::getSoundCard)
                .get(SoundCard::getUsb)
                .get(USB::getVersion).value();

        assertNull(version);

        computer = new Computer();
        computer.setSoundCard(new SoundCard());
        computer.getSoundCard().setUsb(new USB());
        computer.getSoundCard().getUsb().setVersion("3.0");

        version  = of(computer)
                .get(Computer::getSoundCard)
                .get(SoundCard::getUsb)
                .get(USB::getVersion).value();

        assertEquals("3.0", version);
    }

    @Test
    void stream() {
        Computer computer = new Computer();
        NullSafe<Computer> nullSafe = of(computer);
        List<String> users = nullSafe.get(Computer::getUsers).value();

        assertNull(users);
        Stream<String> streamUsers =  nullSafe.get(Computer::getUsers).stream();
        assertTrue(streamUsers.findAny().isEmpty());
        computer.setUsers(Arrays.asList("Admin", "User", "Power User"));

        users = nullSafe.get(Computer::getUsers).value();
        assertNotNull(users);

        assertFalse(nullSafe.get(Computer::getUsers).stream(String.class).findAny().isEmpty());
        assertEquals(3, nullSafe.get(Computer::getUsers).stream(String.class).count());

        assertTrue(nullSafe.get(Computer::getSoundCard).stream().findAny().isEmpty());
        computer.setSoundCard(new SoundCard());
        assertEquals(1, nullSafe.get(Computer::getSoundCard).stream().count());
    }

    @Test
    void isNull() {
        Computer computer = null;
        NullSafe<Computer> nullSafe = of(computer);
        assertTrue(nullSafe.isNull());
        computer = new Computer();
        assertFalse(of(computer).isNull());
    }
}