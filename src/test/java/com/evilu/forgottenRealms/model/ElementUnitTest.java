package com.evilu.forgottenRealms.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

/**
* ElementUnitTest
*/
public class ElementUnitTest {

	@Test
	public void testEqualAffinity() {
		Arrays.stream(Element.values())
			.forEach(element -> assertThat(element)
					.isEqualTo(0d));
	}

	@Test
	public void printAffinities() {
		Arrays.stream(Element.values())
			.forEach(element1 -> Arrays.stream(Element.values())
					.forEach(element2 -> System.out.println(String.format("Affinity between %s and %s: %d", element1.name(), element2.name(), (int) (element1.getAffinity(element2) * 100)))));
	}


	
}
