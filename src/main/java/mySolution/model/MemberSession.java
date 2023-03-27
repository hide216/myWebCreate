package mySolution.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component
@SessionScope
public class MemberSession implements Serializable {
	private String memId;
	private String mailAddress;
}