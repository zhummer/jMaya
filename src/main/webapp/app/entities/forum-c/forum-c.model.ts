import { BaseEntity } from './../../shared';

export class ForumC implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public topics?: BaseEntity[],
    ) {
    }
}
