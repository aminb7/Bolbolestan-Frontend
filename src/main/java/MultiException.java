import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class MultiException extends Exception {
	private List<String> messages;

	@Override
	public String getMessage() {
		String result = "";
		try {
			result = new ObjectMapper().writeValueAsString(messages);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}

	public boolean getHasError() {
		return !messages.isEmpty();
	}
}
