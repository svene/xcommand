package org.collage.template;

import java.io.Writer;

/**
 * This class is supposed to replace TemplateCV but since the property 'writer' in there is user
 * friendly this cannot be done easily at the moment
 * TODO: think about a way to support userfriendly properties
 */
public interface ITemplateCV {
    Writer getWriter();

    void setWriter(Writer aWriter);
}
