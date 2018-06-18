package com.iteye.wwwcomy.authservice.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

public class PasswordBuilder {
	private int size = 16;
	private List<Character> options = new ArrayList<>();
	private Map<List<Character>, Integer> musts = new java.util.LinkedHashMap<>();
	private static SecureRandom secureRandom = new SecureRandom();

	private PasswordBuilder() {
	}

	public PasswordBuilder setSize(int size) {
		this.size = size;
		return this;
	}

	public PasswordBuilder addRangeOption(char from, char to, int mustCount) {
		List<Character> option = new ArrayList<>(to - from + 1);
		for (char i = from; i < to; ++i) {
			option.add(i);
		}
		return addOption(option, mustCount);
	}

	public PasswordBuilder addCharsOption(String chars, int mustCount) {
		List<Character> charList = new ArrayList<>();
		char[] charArr = chars.toCharArray();
		for (char c : charArr) {
			charList.add(c);
		}
		return addOption(charList, mustCount);
	}

	public PasswordBuilder addOption(List<Character> option, int mustCount) {
		this.options.addAll(option);
		musts.put(option, mustCount);
		return this;

	}

	public String build() {
		validateMustsNotOverflowsSize();
		Character[] password = new Character[size];

		// Generate random from musts
		for (Map.Entry<List<Character>, Integer> entry : musts.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				int charIndex = secureRandom.nextInt(entry.getKey().size());
				char c = entry.getKey().get(charIndex);
				addChar(password, c);
			}
		}

		// Generate from overall
		for (int i = 0; i < password.length; i++) {
			if (password[i] != null)
				continue;
			password[i] = options.get(secureRandom.nextInt(options.size()));
		}
		return new String(ArrayUtils.toPrimitive(password));
	}

	private void addChar(Character[] password, char c) {
		int i;
		for (i = secureRandom.nextInt(password.length); password[i] != null; i = secureRandom
				.nextInt(password.length)) {
		}
		password[i] = c;
	}

	private void validateMustsNotOverflowsSize() {
		int overallMusts = 0;
		for (Integer mustCount : musts.values()) {
			overallMusts += mustCount;
		}
		if (overallMusts > size) {
			throw new RuntimeException("Overall musts exceeds the requested size of the password.");
		}
	}

	public static PasswordBuilder getDefaultBuilder() {
		return new PasswordBuilder().addCharsOption("!@#$%&*()_-+=[]{}\\|:/?.,><", 1).addRangeOption('A', 'Z', 1)
				.addRangeOption('a', 'z', 0).addRangeOption('0', '9', 1);
	}

}
