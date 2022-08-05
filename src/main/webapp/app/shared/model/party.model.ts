import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IPartyType } from 'app/shared/model/party-type.model';
import { IStatus } from 'app/shared/model/status.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IParty {
  id?: number;
  isOrganisation?: boolean;
  organisationName?: string;
  organisationShortName?: string;
  salutation?: string;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  gender?: Gender;
  birthDate?: Moment;
  primaryPhone?: string;
  primaryEmail?: string;
  isTemporaryPassword?: boolean;
  logoImageUrl?: string;
  profileImageUrl?: string;
  notes?: string;
  birthdate?: Moment;
  dateOfJoining?: Moment;
  trainingCompletedDate?: Moment;
  jdApprovedOn?: Moment;
  employeeId?: string;
  authString?: any;
  userGroupString?: any;
  tanNo?: string;
  panNo?: string;
  gstNo?: string;
  aadharNo?: string;
  user?: IUser;
  partyType?: IPartyType;
  status?: IStatus;
}

export class Party implements IParty {
  constructor(
    public id?: number,
    public isOrganisation?: boolean,
    public organisationName?: string,
    public organisationShortName?: string,
    public salutation?: string,
    public firstName?: string,
    public middleName?: string,
    public lastName?: string,
    public gender?: Gender,
    public birthDate?: Moment,
    public primaryPhone?: string,
    public primaryEmail?: string,
    public isTemporaryPassword?: boolean,
    public logoImageUrl?: string,
    public profileImageUrl?: string,
    public notes?: string,
    public birthdate?: Moment,
    public dateOfJoining?: Moment,
    public trainingCompletedDate?: Moment,
    public jdApprovedOn?: Moment,
    public employeeId?: string,
    public authString?: any,
    public userGroupString?: any,
    public tanNo?: string,
    public panNo?: string,
    public gstNo?: string,
    public aadharNo?: string,
    public user?: IUser,
    public partyType?: IPartyType,
    public status?: IStatus
  ) {
    this.isOrganisation = this.isOrganisation || false;
    this.isTemporaryPassword = this.isTemporaryPassword || false;
  }
}
