/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 12:11
 */

package fr.strow.api;

import com.google.inject.Injector;
import com.google.inject.Module;

public interface StrowAPI {

    Injector createInjector(Module... modules);
}
