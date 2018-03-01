import { BaseEntity } from './../../shared';

export class HostC implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public firstContactName?: string,
        public lastContactName?: string,
        public address?: string,
        public city?: string,
        public phone1?: string,
        public phone2?: string,
        public phone3?: string,
        public email?: string,
        public username?: string,
        public password?: string,
        public accomodations?: BaseEntity[],
    ) {
    }
}
