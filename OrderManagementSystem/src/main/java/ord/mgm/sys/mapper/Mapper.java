/**
 * 
 */
package ord.mgm.sys.mapper;

import java.util.Optional;

/**
 * @author priya
 *
 */
public interface Mapper<E,D> {
	
	Optional<E> toEntity(D dto);
	
	Optional<D> toDto(E entity);

}
