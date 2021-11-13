package org.xcommand.core;

import lombok.Builder;
import lombok.Value;

import java.util.Map;
import java.util.function.Supplier;

@Builder(toBuilder = true)
@Value
public class DynaBeanOptions {
	IDynaBeanKeyProvider dynaBeanKeyProvider;
	Supplier<Map<String, Object>> contextProvider;
}
