package com.example.flowglobalassignment.global.exception.error;

public class IpRuleMaximumException extends RuntimeException{
        public IpRuleMaximumException() {
            super();
        }
        public IpRuleMaximumException(String message, Throwable cause) {
            super(message, cause);
        }
        public IpRuleMaximumException(String message) {
            super(message);
        }
        public IpRuleMaximumException(Throwable cause) {
            super(cause);
        }
}
