package it.laskaridis.services.membershipservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class MemberTests {

    @Test
    public void shouldSerializeToJson() throws JsonProcessingException {
        // Given
        Member aMember = new Member();
        aMember.setName("name");
        aMember.setEmail("name@localhost");
        aMember.setBusinessPhone("+111111111");
        aMember.setHomePhone("+222222222");
        aMember.setMobilePhone("+333333333");

        // When
        ObjectWriter jsonWriter = new ObjectMapper().writerFor(Member.class);
        String json = jsonWriter.writeValueAsString(aMember);

        // Then
        assertThat(json, isJson());
        assertThat(json, hasJsonPath("$.email", equalTo(aMember.getEmail())));
        assertThat(json, hasJsonPath("$.name", equalTo(aMember.getName())));
        assertThat(json, hasJsonPath("$.businessPhone", equalTo(aMember.getBusinessPhone())));
        assertThat(json, hasJsonPath("$.homePhone", equalTo(aMember.getHomePhone())));
        assertThat(json, hasJsonPath("$.mobilePhone", equalTo(aMember.getMobilePhone())));
    }

    @Test
    public void shouldCreateFromJson() throws IOException {
        // Given
        String json = "{" +
            "\"name\": \"name\", " +
            "\"email\": \"name@localhost\", " +
            "\"businessPhone\": \"+111111111\", " +
            "\"homePhone\": \"+222222222\", " +
            "\"mobilePhone\": \"+333333333\"" +
        "}";

        // When
        ObjectReader jsonReader = new ObjectMapper().readerFor(Member.class);
        Member member = jsonReader.readValue(json);

        // Then
        assertThat(member.getName(), is(equalTo("name")));
        assertThat(member.getEmail(), is(equalTo("name@localhost")));
        assertThat(member.getBusinessPhone(), is(equalTo("+111111111")));
        assertThat(member.getHomePhone(),is(equalTo("+222222222")));
        assertThat(member.getMobilePhone(), is(equalTo("+333333333")));
    }
}
