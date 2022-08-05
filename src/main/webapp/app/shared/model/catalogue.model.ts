export interface ICatalogue {
  id?: number;
  name?: string;
  description?: string;
  sequenceNo?: number;
  imagePath?: string;
  mobileImagePath?: string;
  altImage1?: string;
  altImage2?: string;
  altImage3?: string;
}

export class Catalogue implements ICatalogue {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public sequenceNo?: number,
    public imagePath?: string,
    public mobileImagePath?: string,
    public altImage1?: string,
    public altImage2?: string,
    public altImage3?: string
  ) {}
}
