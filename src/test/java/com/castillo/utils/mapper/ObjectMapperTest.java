package com.castillo.utils.mapper;

import com.castillo.utils.pojos.Source;
import com.castillo.utils.pojos.Target;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMapperTest {

    @Test
    void addBinding() {
        ObjectMapper<Source, Target> mapper = new ObjectMapper<>(Target::new);
        mapper.addBinding(Source::getId, Target::setId);
        Source source = new Source(1L,"test","10");
        Target target = mapper.map(source);
        assertEquals(source.getId(), target.getId());
        assertNull(target.getAge());
        assertNull(target.getName());
        assertNull(target.getInner());
    }

    @Test
    void addBindingWithMapFunction() {
        ObjectMapper<Source, Target> mapper = new ObjectMapper<>(Target::new);
        mapper.addBinding(Source::getAge, Target::setAge,(ageString)-> Integer.parseInt(ageString));//Long lambda, Integer::parseInt have the same result
        Source source = new Source(1L,"test","10");
        Target target = mapper.map(source);
        assertEquals(source.getAge(), target.getAge().toString());
        assertEquals(10, target.getAge());
        assertNull(target.getId());
        assertNull(target.getName());
        assertNull(target.getInner());
    }

    @Test
    void addBindingWithMapper() {
        ObjectMapper<Source, Target> mapper = new ObjectMapper<>(Target::new);
        mapper.addBinding(Source::getId, Target::setId);
        mapper.addBinding(Source::getInner, Target::setInner, mapper);
        Source source = new Source(1L,"test","10");
        source.setInner(new Source(0L,"A","1000"));
        Target target = mapper.map(source);
        assertNull(target.getAge());
        assertNull(target.getName());
        assertNotNull(target.getInner());
        assertEquals(source.getId(), target.getId());
        assertEquals(source.getInner().getId(), target.getInner().getId());
        assertNull(target.getInner().getAge());
        assertNull(target.getInner().getName());
    }

    @Test
    void defaultGroup(){
        ObjectMapper<Source, Target> mapper = new ObjectMapper<>(Target::new);
        List<BindingGrouper> grouperList = new ArrayList<>();

        grouperList.add(mapper.addBinding(Source::getId, Target::setId));
        grouperList.add(mapper.addBinding(Source::getName, Target::setName));
        mapper.addBinding(Source::getAge, Target::setAge, Integer::parseInt);

        final Source source = new Source(1L, "test", "1000");
        //when mapping all the properties below to the default group always
        Target target = mapper.map(source);
        assertEquals(1L, target.getId());
        assertEquals("test", target.getName());
        assertEquals(1000, target.getAge());
        //but the default group can be customized
        grouperList.forEach(bindingGrouper -> bindingGrouper.addToGroup(ObjectMapper.DEFAULT_GROUP));

        target = mapper.map(source);
        assertEquals(1L, target.getId());
        assertEquals("test", target.getName());
        //AGE must be null now as isn't in the default Group
        assertNull(target.getAge());
    }

    @Test
    void customGroup(){
        ObjectMapper<Source, Target> mapper = new ObjectMapper<>(Target::new);
        final String GROUP = "ID-GROUP";
        ObjectMapper.group(GROUP,
        mapper.addBinding(Source::getId, Target::setId),
        mapper.addBinding(Source::getName, Target::setName)
        );
        mapper.addBinding(Source::getAge, Target::setAge, Integer::parseInt);

        final Source source = new Source(1L, "test", "1000");
        //the default group is called
        Target target = mapper.map(source);
        assertEquals(1L, target.getId());
        assertEquals("test", target.getName());
        assertEquals(1000, target.getAge());

        //the map is called with a custom group
        target = mapper.map(source, GROUP);
        assertEquals(1L, target.getId());
        assertEquals("test", target.getName());
        //AGE must be null now as isn't in the default Group
        assertNull(target.getAge());
    }


    @Test
    void addBindingWithMapperSubGroups() {
        ObjectMapper<Source, Target> mapper = new ObjectMapper<>(Target::new);
        final String GROUP = "ID-GROUP";
        ObjectMapper.group(GROUP,
                mapper.addBinding(Source::getId, Target::setId),
                mapper.addBinding(Source::getName, Target::setName)
        );
        mapper.addBinding(Source::getAge, Target::setAge, Integer::parseInt);
        mapper.addBinding(Source::getInner, Target::setInner, mapper, GROUP);// add the custom group to the subMapper

        Source source = new Source(1L, "test", "100");
        source.setInner(new Source(0L, "test 0", "1"));
        source.getInner().setInner(new Source(-1L, "", ""));

        Target target = mapper.map(source);


        assertNotNull(target.getName());
        assertNotNull(target.getName());
        assertNotNull(target.getAge());
        assertNotNull(target.getInner());
        assertNotNull(target.getInner().getId());
        assertNotNull(target.getInner().getName());
        assertNull(target.getInner().getAge());//age and inner are null as are not included in the sub group
        assertNull(target.getInner().getInner());


    }

    @Test
    void mapToUpdateObject() {
        ObjectMapper<Source, Target> mapper = new ObjectMapper<>(Target::new);
        mapper.addBinding(Source::getId, Target::setId);
        mapper.addBinding(Source::getName, Target::setName);
        mapper.addBinding(Source::getAge, Target::setAge, Integer::parseInt);

        Source source = new Source(10L, null, "20");
        Target target = new Target(20L, "", 15);
        mapper.map(source, target);

        assertEquals(source.getId(), target.getId());
        assertEquals(source.getName(), target.getName());
        assertEquals(source.getAge(), target.getAge().toString());

    }

}