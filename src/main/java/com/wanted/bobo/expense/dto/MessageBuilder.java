package com.wanted.bobo.expense.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {

    private String title;

    public MessageBuilder(String title) {
        this.title = title;
    }

    public Map<String, Object> build() {
        Map<String, Object> message = new HashMap<>();
        message.put("embeds", Collections.singletonList(createEmbed()));
        return message;
    }

    private Map<String, Object> createEmbed() {
        Map<String, Object> embed = new HashMap<>();
        embed.put("title", title);
        embed.put("description", " ");
        embed.put("color", 0x808080);
        return embed;
    }

}
