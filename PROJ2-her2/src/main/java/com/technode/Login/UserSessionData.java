package com.technode.Login;

import java.util.List;

public class UserSessionData {
    private List<TabData> tabs;

    public List<TabData> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabData> tabs) {
        this.tabs = tabs;
    }

    public static class TabData {
        private String title;
        private List<String> chatMessages;

        public TabData() {}

        public TabData(String title, List<String> chatMessages) {
            this.title = title;
            this.chatMessages = chatMessages;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getChatMessages() {
            return chatMessages;
        }

        public void setChatMessages(List<String> chatMessages) {
            this.chatMessages = chatMessages;
        }
    }
}
