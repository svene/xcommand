package org.collage.template;

import java.io.Writer;

/**
 * This class is supposed to replace TemplateCV but since the property 'writer' in there is user
 * friendly this cannot be done easily at the moment
 * TODO: think about a way to support userfriendly properties
 */
public interface ITemplateCV {
    public Writer getWriter();

    public void setWriter(Writer aWriter);
}
